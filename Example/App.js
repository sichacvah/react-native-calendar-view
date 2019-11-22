/**
 * Sample React Native App
 * https://github.com/facebook/react-native
 *
 * @format
 * @flow
 */

import React from 'react';
import {
  StyleSheet,
  Animated,
  Text,
  Platform,
  View
} from 'react-native';

import { CalendarView } from 'react-native-calendar-view'


const now = new Date()
const format = (date) => {
  return date.toISOString().slice(0, 10)
}
const addDays = (date, days) => {
  return new Date(date.getFullYear(), date.getMonth(), date.getDate() + days)
}
const markedDates = {
  [format(addDays(now, 1))]: {
    starting: {
      color: 'red',
    }
  },
  [format(addDays(now, 2))]: {
    color: 'pink'
  },
  [format(addDays(now, 3))]: {
    color: 'pink'
  },
  [format(addDays(now, 4))]: {
    color: 'pink'
  },
  [format(addDays(now, 5))]: {
    ending: {
      color: 'red'
    }
  },
  [format(addDays(now, 10))]: {
    starting: {
      color: 'green',
    }
  },
  [format(addDays(now, 11))]: {
    color: 'lightseagreen',
    textColor: 'white'
  },
  [format(addDays(now, 12))]: {
    color: 'lightseagreen',
    textColor: 'white'

  },
  [format(addDays(now, 13))]: {
    color: 'lightseagreen',
    textColor: 'white'

  },
  [format(addDays(now, 14))]: {
    ending: {
      color: 'green'
    }
  }
}

export default class App extends React.Component {
  render() {
    return (
      <CalendarView theme={{ monthTitleColor: 'purple' }}  markedDates={markedDates}  />
    )
  }
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    borderColor: 'transparent'
  }
});