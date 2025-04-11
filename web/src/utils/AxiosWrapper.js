import axios from 'axios';
import Crypto from './Crypto.js';

export const exchange = async () => {
    const crypto = new Crypto();
    crypto.generateRsaKey();

    axios.defaults.headers.common["x-tlspj-key"] = crypto.getRsaPublicKey();

    const res = await axios.post('/exchange');

    if (res.status === 200 && res.data?.header?.code === '00000') {
        crypto.setToken(res.data?.body?.token);
        crypto.setRsaServerPublicKey(res.data?.body?.key);
    }

    return crypto;
};

export const AxiosWrapper = (crypto) => {

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

            const decData = crypto.aesDec(data.data, crypto.rsaDec(data.key));

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