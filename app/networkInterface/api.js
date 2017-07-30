import {
    ToastAndroid,
} from 'react-native';

const nutritionValue = {
    "foods": [
        {
            "food_name": "Orange",
            "serving_qty": 1,
            "serving_unit": "fruit (2-7/8\" dia)",
            "serving_weight_grams": 140,
            "nf_calories": 68.6,
            "nf_total_fat": 0.21,
            "nf_saturated_fat": 0.02,
            "nf_cholesterol": 0,
            "nf_sodium": 1.4,
            "nf_total_carbohydrate": 17.56,
            "nf_dietary_fiber": 3.08,
            "nf_sugars": 11.9,
            "nf_protein": 1.27,
            "nf_potassium": 232.4
        }, {
            "food_name": "Apple",
            "serving_qty": 1,
            "serving_unit": "fruit (2-7/8\" dia)",
            "serving_weight_grams": 140,
            "nf_calories": 68.6,
            "nf_total_fat": 0.21,
            "nf_saturated_fat": 0.02,
            "nf_cholesterol": 0,
            "nf_sodium": 1.4,
            "nf_total_carbohydrate": 17.56,
            "nf_dietary_fiber": 3.08,
            "nf_sugars": 11.9,
            "nf_protein": 1.27,
            "nf_potassium": 232.4
        }
    ]
}

export const fetchNutritionInfo = (foodName) => {
    return call(`https://health-vision.herokuapp.com/nutrition/${foodName}`, 'GET')
        .then((re)=>re.json())
        .then((res) => res).catch((e) => {
            ToastAndroid.show(`Unable to found nutrition info for ${foodName}`,0);
            //console.warn(' 111 error from nutrition info is ... ', e)
        })
}

export const getFruitName = (fruitEncodedData) => {
    return call(`http://www.google.co.in?foodName=${fruitEncodedData}`).then((res) => {
        console.log(' res ', res)
    }).catch((e) => {

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

