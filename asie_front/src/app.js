import React, { Component } from 'react';
import Header from './components/header/header'
import Login from './components/login/login'
import axios from 'axios';

class App extends Component {

  async getAuth() {
    var body = {};
    var headers = { 'Authorization': localStorage.getItem("token"), 'Content-Type': 'application/json' };
    axios.post('http://localhost:8080/token/refresh', body, { headers })
      .then(response => {
        localStorage.setItem("token", response.data.token);
        this.setState({
          auth: true
        });
      })
      .catch(errorM => {
        this.setState({
          auth: false
        });
      })
      ;
  }

  constructor(props) {
    super(props);
    this.state = { auth: false };

    this.setWrapperRef = this.setWrapperRef.bind(this);
    this.handleClickOutside = this.handleClickOutside.bind(this);
  }

  componentDidMount() {
    document.addEventListener('mousedown', this.handleClickOutside);
    document.addEventListener('keypress', this.handleClickOutside);
    this.getAuth();
  }

  componentWillUnmount() {
    document.removeEventListener('mousedown', this.handleClickOutside);
    document.removeEventListener('keypress', this.handleClickOutside);
  }

  setWrapperRef(node) {
    this.wrapperRef = node;
  }

  handleStatusChange() {
    this.setState({
      auth: true
    });
  }

  handleClickOutside(event) {
    if (event.keyCode === 32 || event.keyCode === 13 || event.which === 1) {
      if (this.state.auth != false) {
        this.getAuth();
      }
    }
  }

  render() {
    if (this.state.auth === false) {
      return (
        <div>
          <Login />
        </div >
      );
    }
    return (
      <div>
        <Header />
      </div>
    );
  }
}

export default App;