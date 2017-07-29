/**
 * Sample React Native App
 * https://github.com/facebook/react-native
 * @flow
 */


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
import Camera from 'react-native-camera';

import App from './app/app.js'

export default class HealthVision extends Component {

  render() {
  return <App/>
    return (
        <Camera
          ref={(cam) => {
            this.camera = cam;
          }}
          style={styles.preview}
          aspect={Camera.constants.Aspect.fill}>
          <Button
            style={styles.capture}
            onPress={this.takePicture.bind(this)}
            title="Detect"
            color="#841584"
            accessibilityLabel="Learn more about this purple button"
          />
        </Camera>
    );
  }

  takePicture() {
    const options = {};
    //options.location = ...
    this.camera.capture({metadata: options})
      .then((data) => console.log(data))
      .catch(err => console.error(err));
  }
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    flexDirection: 'row',
  },
  preview: {
    flex: 1,
    justifyContent: 'flex-end',
    alignItems: 'center'
  },
  capture: {
    backgroundColor: '#fff',
    borderRadius: 10,
    color: '#000',
    padding: 10,
    margin: 40
  }
});

AppRegistry.registerComponent('HealthVision', () => HealthVision);