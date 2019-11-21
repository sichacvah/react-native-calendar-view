import { PureComponent, Component, ComponentClass } from 'react'
import { findNodeHandle, requireNativeComponent, StyleSheet, View, processColor, SafeAreaView, Animated } from 'react-native'

const defaultTheme = {
  pastDayColor: 'gray',
  pastDayFontFamily: 'Roboto',
  pastDayFontWeight: '400',
  pastDayFontSize: 16,

  dayColor: 'black',
  dayBackgroundColor: 'white',
  dayFontFamily: 'Roboto',
  dayFontSize: 16,
  dayFontWeight: '400',
  dayUnderlayColor: '#ddd',
  borderColor: 'silver',

  headerBackgroundColor: 'white',
  monthTitleColor: 'silver',
  monthTitleFontFamily: 'Roboto',
  monthTitleFontSize: 20,
  monthTitleFontWeight: 'bold',
  weekdayColor: 'black',
  weekdayBackgroundColor: 'white',
  weekdayFontFamily: 'Roboto',
  weekdayFontSize: 16,
  weekdayFontWeight: '400',
  selectionColor: 'palevioletred',
  selectionEdgeColor: 'blueviolet',
  selectionTextColor: 'black'
}



const RNCalendarView = requireNativeComponent('CalendarView')


const processMarkedDateBase = (markedDate) => ({
  ...markedDate,
  color: processColor(markedDate.color),
  textColor: processColor(markedDate.textColor)
})


const processMarkedDates = (markedDates) => {
  return Object.keys(markedDates).reduce((acc, key) => {
    const markedDate = markedDates[key]
    const starting = markedDate.starting
    const ending = markedDate.ending
    return {
      ...acc,
      [key]: {
        ...processMarkedDateBase(markedDate),
        ...(starting ? {starting: processMarkedDateBase(starting)} : undefined),
        ...(ending ? {ending: processMarkedDateBase(ending)} : undefined)
      }
    }
  }, {})
}



class WrappedRNCalendarView extends Component {

  render() {
    return (
      <RNCalendarView
          {...this.props}
          ref={(ref) => this._nativeViewRef = ref}
        />
    )
  }

  getScrollableNode() {
    return findNodeHandle(this._nativeViewRef)
  }
}

const AnimatedRNCalendarView = Animated.createAnimatedComponent(WrappedRNCalendarView)




export class CalendarView extends PureComponent {

  constructor(props) {
    super(props)
    if (this.props.onScroll) {
      this.valueX = new Animated.Value(0)
      this.event = Animated.event([{
        nativeEvent: {
          x: this.valueX,
          y: this.props.onScroll || new Animated.Value(0)
        }
      }], { useNativeDriver: true })
    }
    this.state = {
      from: undefined,
      to: undefined
    }
  }

  callChangeState = () => {
    if (this.props.onSelectionChange) {
      this.props.onSelectionChange(this.state)
    }
  }

  changeState = (state) => {
    this.setState(state, this.callChangeState)
  }

  onDatePress = ({ nativeEvent: { date } }) => {
    const { from, to } = this.state
  
    if (!from && !to) {
      return this.changeState({ from: date })
    }
    if (from && !to) {
      if (date === from) {
        return this.changeState({ from: undefined })
      }
      if ((new Date(date)).getTime() < (new Date(from)).getTime()) {
        return this.changeState({ from: date, to: from })
      }
      return this.changeState({ to: date })
      
    }
    if (from && to) {
      return this.changeState({ from: date, to: undefined })
    }
  }

  render () {
    const {
      markedDates = {},
      theme = defaultTheme
    } = this.props
    const nativeTheme = {
      ...theme,
      headerBackgroundColor: processColor(theme.headerBackgroundColor),
      weekdayColor: processColor(theme.weekdayColor),
      monthTitleColor: processColor(theme.monthTitleColor),
      weekdayBackgroundColor: processColor(theme.weekdayBackgroundColor),
      dayColor: processColor(theme.dayColor),
      dayBackgroundColor: processColor(theme.dayBackgroundColor),
      borderColor: processColor(theme.borderColor),
      pastDayColor: processColor(theme.pastDayColor),
      selectionColor: processColor(theme.selectionColor),
      selectionEdgeColor: processColor(theme.selectionEdgeColor),
      selectionTextColor: processColor(theme.selectionTextColor)
    }

    return (
      <View style={styles.container}>
        <SafeAreaView style={styles.container}>
          <AnimatedRNCalendarView
            from={this.state.from}
            to={this.state.to}
            onDatePress={this.onDatePress}
            onAnimatedEvent={this.event ? this.event : undefined}
            style={styles.rnCalendar}
            theme={nativeTheme}
            markedDates={processMarkedDates(markedDates)}
          />
        </SafeAreaView>
      </View>
    )
  }
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: 'white'
  },
  rnCalendar: {
    flex: 1,
    overflow: 'hidden'
  }
})
