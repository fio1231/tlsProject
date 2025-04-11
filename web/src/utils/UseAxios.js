import {AxiosWrapper, exchange} from './AxiosWrapper.js'

const get = async (uri, config) => {
    return await AxiosWrapper(await exchange()).get(uri, config);
}

const post = async (uri, data, config) => {
    return await AxiosWrapper(await exchange()).post(uri, data, config);
}

export {get, post}