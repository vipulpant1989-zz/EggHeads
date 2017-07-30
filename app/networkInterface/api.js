import {
    ToastAndroid,
} from 'react-native';

export const fetchNutritionInfo = (foodName) => {
    return call(`https://health-vision.herokuapp.com/nutrition/${foodName}`, 'GET')
        .then((re)=>re.json())
        .then((res) => res).catch((e) => {
            ToastAndroid.show(`Unable to found nutrition info for ${foodName}`,0);
            //console.warn(' 111 error from nutrition info is ... ', e)
        })
}

export const getFruitName = (fruitEncodedData) => {
    return callWithBody(`http://health-vision.herokuapp.com/detect`,{base64: fruitEncodedData}).then((res) => {
        console.log(' res ', res)
    }).catch((e) => {
        ToastAndroid.show(`Unable to found fruit name for given image, please give us one more try`,0);
    })

}

function call(url, method = 'POST') {
    return fetch(url, {
        method,
        headers: {
            'Accept': 'application/json',
            'Content-type': 'application/json'
        }/*,
         body: JSON.stringify({param: 'value'})*/
    }).catch((e) => {
        ToastAndroid.show('Error ' + e && e.message, ToastAndroid.SHORT)
        throw e;
    })
}

function callWithBody(url,  params={}) {
    return fetch(url, {
        method : 'POST',
        headers: {
            'Accept': 'application/json',
            'Content-type': 'application/json'
        },
         body: JSON.stringify(params)
    })
}

