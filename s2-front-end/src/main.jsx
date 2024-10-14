import React from 'react'
import {Provider} from 'react-redux'
import ReactDOM from 'react-dom/client'
import App from './App.jsx'
import './index.css'
import { combineReducers } from 'redux'
import { legacy_createStore as createStore} from 'redux'
import customers from './reducers/customers'
import formState from './reducers/formState'
import appState from './reducers/appState'
import events from './reducers/events'
import registrations from './reducers/registrations'
import login from './reducers/login';

const appReducer = combineReducers({formState,appState,customers, events, login, registrations})
let store = createStore( appReducer )

ReactDOM.createRoot(document.getElementById('root')).render(
  <React.StrictMode>
    <Provider store={store}>
      <App />
    </Provider>,
  </React.StrictMode>,
)
