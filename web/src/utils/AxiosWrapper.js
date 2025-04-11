import axios from 'axios';
import Crypto from './Crypto.js';

/**
 * 키교환 통신
 * @returns {Promise<Crypto>}
 */
export const exchange = async () => {
    const crypto = new Crypto();
    crypto.generateRsaKey();

    // 클라이언트의 공개 키
    axios.defaults.headers.common["x-tlspj-key"] = crypto.getRsaPublicKey();

    const res = await axios.post('/exchange');

    if (res.status === 200 && res.data?.header?.code === '00000') {
        // 발급된 토큰과 서버의 공개키를 crypto 클래스 내부에 저장
        crypto.setToken(res.data?.body?.token);
        crypto.setRsaServerPublicKey(res.data?.body?.key);
    }

    return crypto;
};

/**
 * Axios 함수화 
 * @param crypto
 * @returns AxiosInstance
 * @constructor
 */
export const AxiosWrapper = (crypto) => {

    // 공통 설정 처리
    const axiosInstance = axios.create({
        baseURL: '/api',
        timeout: 50000,
        headers: {
            "Content-Type": "application/json"
        }
    });

    // 요청 인터셉터
    axiosInstance.interceptors.request.use(function (config) {
        crypto.generateAesKey();

        // 데이터 암호화 처리
        const encData = crypto.aesEnc(JSON.stringify(config.data));

        config.data = encData;
        config.headers['x-tlspj-token'] = crypto.getToken();
        config.headers['x-tlspj-key'] = crypto.rsaEnc(crypto.getAesKey(), crypto.getRsaServerPublicKey());

        return config;
    }, function (error) {
        return Promise.reject(error);
    });

    // 응답 인터셉터
    axiosInstance.interceptors.response.use(function (response) {
        const {data, code, msg} = response.data;

        if(data && '' !== data) {

            // 데이터 복호화 처리
            const decData = crypto.aesDec(data.data, crypto.rsaDec(data.key));

            // 데이터 재가공
            const resData = {
                resultCode: code
                , resultMsg: msg
                , resultData: JSON.parse(decData)
            }

            response.data = resData;
        }
        return response.data;
    }, function (error) {
        return Promise.reject(error);
    });

    return axiosInstance;

};