'use strict';
import React, { Component } from 'react';
import {
    CameraRoll,
    AppRegistry,
    Dimensions,
    StyleSheet,
    Text,
    TouchableHighlight,
    View,
    Button,
    ToastAndroid,
    TouchableOpacity,
    Image
} from 'react-native';
import Camera from 'react-native-camera';
import {getFruitName} from '../../networkInterface/api.js'


export default class ComCam extends Component {

    constructor(p) {
        super(p);
        this.state = {
            showLoading: false
        }
        this.onNext = this.onNext.bind(this);
    }

    setLoading(showLoading) {
        this.setState({showLoading})
    }

    onNext(data, fruitName) {
        this.props.setActiveView(2, data, fruitName)

    }

    render() {
        const {showLoading} = this.state;
        return (
            <View style={styles.wrapper}>
                <Camera
                    ref={(cam) => {
            this.camera = cam;
          }}
                    orientation={Camera.constants.Orientation.auto }
                    style={styles.preview}
                    captureTarget={Camera.constants.CaptureTarget.memory}
                    aspect={Camera.constants.Aspect.fill}>
                    <Button
                        style={styles.capture}
                        onPress={this.takePicture.bind(this)}
                        title={showLoading? 'Processing...':'Detect'}
                        color="#841584"
                        accessibilityLabel="Learn more about this purple button"
                        disabled={showLoading}
                        />
                </Camera>
            </View>


        );
    }

    takePicture() {
        const options = {};
        this.setLoading(true)
        this.camera.capture({metadata: options})
            .then((img) => {
                const imgAs64Bit = `data:image/jpg;base64,${img.data}`
                this.setLoading(false)
                this.onNext(imgAs64Bit, 'apple')
                //getFruitName(imgAs64Bit).then((fruitName) => {
                //    //return this.onNext(imgAs64Bit, fruitName)
                //})

            })
            .catch(err => {
                this.setLoading(false)
                ToastAndroid.show('Error while saving image ' + err.message, ToastAndroid.SHORT)
                console.error(err)
            });
    }
}

const styles = StyleSheet.create({
    wrapper: {
        flex: 1
    },
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
        flex: 0,
        backgroundColor: '#fff',
        borderRadius: 5,
        color: '#000',
        padding: 10,
        margin: 40
    }
});