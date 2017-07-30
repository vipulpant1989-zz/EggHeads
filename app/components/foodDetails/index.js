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
            foodsNutritionValue: {}
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
        const {food_name='FOOD_NAME_TO_BE_DETECTED'} = this.props;
        const nuts = [
            <View key={food_name} style={{borderWidth: 1, borderStyle: 'solid', padding:5, margin: 5}}>
                <View style={styles.foodNameContainer}><Text style={styles.foodName}>{food_name}</Text></View>
            </View>
        ];
        for (let curNut in foodsNutritionValue) {
            var {val, unit} = foodsNutritionValue[curNut]
            val && nuts.push(
                <View key={curNut} style={styles.nutsInfo}>
                    <Text style={styles.nutsKey}>{curNut}</Text>
                    <Text style={styles.nutsValue}>{`${val} ${(unit) ? unit :' '}`}</Text>
                </View>
            )
        }
        return <View style={styles.nutsContainer}>
            <View ><Text style={styles.title}>Nutrition facts</Text></View>
            {nuts ? nuts : null}
        </View>
    }

    renderBurnCalories() {
        const {foodsNutritionValue} = this.state;
        const totalCalories = foodsNutritionValue.energyKcal && foodsNutritionValue.energyKcal && foodsNutritionValue.energyKcal.val
        return totalCalories && !isNaN(totalCalories) && (
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
                    <Text style={styles.nutsValue}>{Math.floor(totalCalories / 1000 * BURN_REF.bicycling)} Minutes</Text>
                </View>

            </View>
        )
    }

    render() {
        const {showLoading} = this.state;
        const { imageData} = this.props;

        const updatedImageData = `data:image/jpg;base64,${imageData}`
        //console.log('111 updatedImageData', updatedImageData)
        return (
            <View style={styles.wrapper}>
                <ScrollView style={styles.container}>
                    {showLoading && <Text>Loading</Text>}
                    {imageData && <Image
                        style={styles.image}
                        source={{uri: updatedImageData}}
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