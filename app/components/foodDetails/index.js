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
  Dimensions
} from 'react-native';

const WINDOW_WIDTH = Dimensions.get('window').width
export default class FoodDetails extends Component {

  constructor(p) {
    super(p);
    this.state = {
      showLoading: false
    }
    this.onPrev = this.onPrev.bind(this);
  }

  setLoading(showLoading){
    this.setState({showLoading})
  }

  onPrev(){
            this.props.setActiveView(1)
  }

  render() {
    const {showLoading} = this.state;
    const { imageData} = this.props;

    return (
        <View style={styles.wrapper}>
      <View style={styles.container}>
<Text>Detail View </Text>
       {imageData && <Image 
         style={styles.image}
          source={{uri: imageData.path}}  
       />}

      </View>
         <Button
            style={styles.actionButton}
            onPress={this.onPrev}
            title="Try again"
            color="#841584"
          />
                </View>
    );
  }


}

const styles = StyleSheet.create({
wrapper:{
  flex:1
},container:{flex:1},
  actionButton: {
    flex: 0,
    backgroundColor: '#fff',
    borderRadius: 5,
    color: '#000',
    padding: 10,
    margin: 40
  },
  image:{
    width:WINDOW_WIDTH,
    height:250
  }
});