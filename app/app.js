'use strict';
import React, { Component } from 'react';
import {
  AppRegistry,
  Dimensions,
  StyleSheet,
  Text,
  TouchableHighlight,
  View,
  Button
} from 'react-native';

import CameraComponent from './components/camera'
import FoodDetails from './components/foodDetails'


export default class App extends Component {
    constructor(p){
        super(p);
        this.state = {
            activeView: 1,
            imageData: undefined
        }
        this.setActiveView = this.setActiveView.bind(this)
    }

    setActiveView(activeView, imageData){
        let updatedState = {activeView}
        if(imageData) {
            updatedState.imageData = imageData
        }
        this.setState(updatedState)
    }

    renderActiveView() {
        const {activeView, imageData} = this.state;
        let renderView = null;

        switch(activeView){
            case 1: {
                renderView =  <CameraComponent setActiveView={this.setActiveView}/>;
                break; 
            }
            case 2: {
                renderView = <FoodDetails imageData={imageData} setActiveView={this.setActiveView} />;
                break;
            }
            

        }
        return renderView

    }

  render() {
    return (
       <View style={styles.container}>
           <View style={styles.titleContainer}><Text style={styles.title}>Health Vision</Text></View>
         {this.renderActiveView()}
       </View>
    );
  }

}


const styles = StyleSheet.create({
  container: {
    flex: 1 
 },titleContainer:{padding: 5},
  title: {
      fontWeight: 'bold', fontSize: 16, color: '#841584', textAlign: 'center'
    }
});