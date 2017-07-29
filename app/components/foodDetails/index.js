'use strict';
import React, { Component } from 'react';
import {
    StyleSheet,
    Text,
    TouchableHighlight,
    View,
    Button,
    ToastAndroid,
    TouchableOpacity,
    Image,
    Dimensions,
    ScrollView
} from 'react-native';

const WINDOW_WIDTH = Dimensions.get('window').width
const WINDOW_HEIGHT = Dimensions.get('window').height

/*wrt 1000Cal or 1Kcal*/
const BURN_REF = {
    walking: 253,
    running: 92,
    bicycling: 138
}


import {fetchNutritionInfo} from '../../networkInterface/api.js';

export default class FoodDetails extends Component {

    constructor(p) {
        super(p);
        this.state = {
            showLoading: false,
            foodsNutritionValue: []
        }
        this.onPrev = this.onPrev.bind(this);
    }

    componentDidMount() {
        const fruitName = this.props.fruitName || 'Orange'
        this.setLoading(true)
        fetchNutritionInfo(fruitName)
            .then((foodsNutritionValue) => {
                this.setLoading(false)
                this.setState({foodsNutritionValue})
            })
            .catch((e) => this.setLoading(false))
    }

    setLoading(showLoading) {
        this.setState({showLoading})
    }

    onPrev() {
        this.props.setActiveView(1)
    }

    renderNutritionValue() {
        const {foodsNutritionValue} = this.state;
        const nuts = foodsNutritionValue.map(({
            food_name, serving_qty, serving_unit ,serving_weight_grams, nf_calories, nf_total_fat, nf_saturated_fat,
            nf_cholesterol, nf_sodium, nf_total_carbohydrate, nf_dietary_fiber, nf_sugars, nf_protein, nf_potassium
            }, index) => {
            return (
                <View key={food_name} style={{borderWidth: 1, borderStyle: 'solid', padding:5, margin: 5}}>
                    <View style={styles.foodNameContainer}><Text style={styles.foodName}>{food_name}</Text></View>

                    <View style={styles.nutsInfo}>
                        <Text style={styles.nutsKey}>Serving qty</Text>
                        <Text style={styles.nutsValue}>{serving_qty}</Text>
                    </View>
                    <View style={styles.nutsInfo}>
                        <Text style={styles.nutsKey}>Serving unit</Text>
                        <Text style={styles.nutsValue}>{serving_unit}</Text>
                    </View>
                    <View style={styles.nutsInfo}>
                        <Text style={styles.nutsKey}>Serving weight in gms</Text>
                        <Text style={styles.nutsValue}>{serving_weight_grams}</Text>
                    </View>
                    <View style={styles.nutsInfo}>
                        <Text style={styles.nutsKey}>Calories</Text>
                        <Text style={styles.nutsValue}>{nf_calories}</Text>
                    </View>
                    <View style={styles.nutsInfo}>
                        <Text style={styles.nutsKey}>Total fat</Text>
                        <Text style={styles.nutsValue}>{nf_total_fat}</Text>
                    </View>
                    <View style={styles.nutsInfo}>
                        <Text style={styles.nutsKey}>Saturated fat</Text>
                        <Text style={styles.nutsValue}>{nf_saturated_fat}</Text>
                    </View>
                    <View style={styles.nutsInfo}>
                        <Text style={styles.nutsKey}>Cholestrol</Text>
                        <Text style={styles.nutsValue}>{nf_cholesterol}</Text>
                    </View>
                    <View style={styles.nutsInfo}>
                        <Text style={styles.nutsKey}>Sodium</Text>
                        <Text style={styles.nutsValue}>{nf_sodium}</Text>
                    </View>
                    <View style={styles.nutsInfo}>
                        <Text style={styles.nutsKey}>Total Carbs</Text>
                        <Text style={styles.nutsValue}>{nf_total_carbohydrate}</Text>
                    </View>
                    <View style={styles.nutsInfo}>
                        <Text style={styles.nutsKey}>Dietary Fiber</Text>
                        <Text style={styles.nutsValue}>{nf_dietary_fiber}</Text>
                    </View>
                    <View style={styles.nutsInfo}>
                        <Text style={styles.nutsKey}>Sugar</Text>
                        <Text style={styles.nutsValue}>{nf_sugars}</Text>
                    </View>
                    <View style={styles.nutsInfo}>
                        <Text style={styles.nutsKey}>Protein</Text>
                        <Text style={styles.nutsValue}>{nf_protein}</Text>
                    </View>
                    <View style={styles.nutsInfo}>
                        <Text style={styles.nutsKey}>Potassium</Text>
                        <Text style={styles.nutsValue}>{nf_potassium}</Text>
                    </View>

                </View>
            )
        });
        return <View style={styles.nutsContainer}>
            <View ><Text style={styles.title}>Nutrition facts</Text></View>
            {nuts}
        </View>
    }

    renderBurnCalories() {
        const {foodsNutritionValue} = this.state;
        const totalCalories = foodsNutritionValue.reduce((sum, {nf_calories}) => sum + nf_calories, 0)
        return (
            <View style={styles.burnCalContainer}>
                <View style={styles.burnCalTitleContainer}>
                    <Text style={styles.burnCalTitle}>How long would it take to burn off {totalCalories} KCal?</Text>
                </View>

                <View style={styles.nutsInfo}>
                    <Text style={styles.nutsKey}>Walking(3 kmph)</Text>
                    <Text style={styles.nutsValue}>{Math.floor(totalCalories / 1000 * BURN_REF.walking)} Minutes</Text>
                </View>

                <View style={styles.nutsInfo}>
                    <Text style={styles.nutsKey}>Running (6mph)</Text>
                    <Text style={styles.nutsValue}>{Math.floor(totalCalories / 1000 * BURN_REF.running)} Minutes</Text>
                </View>

                <View style={styles.nutsInfo}>
                    <Text style={styles.nutsKey}>Bicycling (10mph)</Text>
                    <Text style={styles.nutsValue}>{Math.floor(totalCalories / 1000 * BURN_REF.bicycling)}
                        Minutes</Text>
                </View>

            </View>
        )
    }

    render() {
        const {showLoading} = this.state;
        const { imageData} = this.props;

        return (
            <View style={styles.wrapper}>
                <ScrollView style={styles.container}>
                    {showLoading && <Text>Loading</Text>}
                    {imageData && <Image
                        style={styles.image}
                        source={{uri: imageData.path}}
                        />}
                    <View>
                        {this.renderNutritionValue()}
                    </View>
                    <View>
                        {this.renderBurnCalories()}
                    </View>

                </ScrollView>
                <Button
                    style={styles.actionButton}
                    onPress={this.onPrev}
                    title="Search again"
                    color="#841584"
                    />
            </View>
        );
    }


}

const styles = StyleSheet.create({
    wrapper: {
        flex: 1
    }, container: {flex: 1},
    actionButton: {
        flex: 0,
        backgroundColor: '#fff',
        borderRadius: 5,
        color: '#000',
        padding: 10,
        margin: 40
    },
    image: {
        width: WINDOW_WIDTH,
        height: 250
    },
    foodNameContainer: {
        padding: 5
    },
    foodName: {
        fontWeight: 'bold', fontSize: 16, color: '#841584', textAlign: 'center'
    },
    nutsContainer: {
        borderWidth: 2, borderStyle: 'solid', margin: 5, padding: 5
    },
    title: {
        fontWeight: 'bold', fontSize: 20, color: 'black', textAlign: 'center'
    },
    nutsKey: {
        color: 'black', width: 200, fontSize: 15
    },
    nutsValue: {
        fontSize: 15, color: '#841584'
    },
    nutsInfo: {
        flexDirection: 'row'
    },
    burnCalTitle: {
        fontWeight: 'bold', fontSize: 15, color: 'black', textAlign: 'center'
    },
    burnCalTitleContainer: {padding: 5},
    burnCalContainer: {
        borderWidth: 2, borderStyle: 'solid', margin: 5, padding: 5
    }
});