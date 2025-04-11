import {AxiosWrapper, exchange} from './AxiosWrapper.js'

/**
 * Get 방식 통신
 * @param uri
 * @param config
 * @returns {Promise<axios.AxiosResponse<any>>}
 */
const get = async (uri, config) => {
    return await AxiosWrapper(await exchange()).get(uri, config);
}

/**
 * POST 방식 통신
 * @param uri
 * @param data
 * @param config
 * @returns {Promise<axios.AxiosResponse<any>>}
 */
const post = async (uri, data, config) => {
    return await AxiosWrapper(await exchange()).post(uri, data, config);
}

export {get, post}