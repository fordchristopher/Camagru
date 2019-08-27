import React from 'react';
import Header from './components/header.js';
import Footer from './components/footer.js';
import Stream from './components/stream.js';
import Account from './components/account.js';
import Post from './components/post.js';
import { BrowserRouter, Route } from 'react-router-dom';
import './App.css';
import { APIUrl, baseURL} from './global.js';

class App extends React.Component {
  constructor() {
    super();
    this.state = {
      user: null
    };
    this.login = this.login.bind(this);
    this.logout = this.logout.bind(this);
    this.showme = this.showme.bind(this);
  }

  login = (username, pass) => {
    // backend
    // check to see if credentials match
    fetch(`${APIUrl}/users/login`, {
      method: 'post',
      headers: {
				'Origin': baseURL,
				'Access-Control-Request-Method': 'POST',
				'Content-Type': 'application/json'
      },
      body: JSON.stringify({
        email: username,
        password: pass
      })
    }).then(res => res.json())
    .then(data => {
      if (typeof(data.data.id) !== 'undefined') {
        this.setState({
          user: data.data
        })
      } else {
        alert(data.data);
      }
    });

    if (false) {
      this.setState({
        user: username
      });
    } //else this.logout();
  };

  logout = (username, pass) => {
    this.setState({
      user: null
    });
  };

  showme = () => {
    console.log(this.state);
  };

  render() {
    return (
      <div className="App">
        <BrowserRouter>
          <Header showme={this.showme} />
          <Route
            path="/"
            render={() => <Account user={this.state.user} login={this.login} />}
            exact
          />
          <Route
            path="/create"
            render={() => <Stream user={this.state.user} />}
          />
          <Route
            path="/home"
            render={() => <Post />}
          />
          <Footer user={this.state.user} logout={this.logout} />
        </BrowserRouter>
      </div>
    );
  }
}

export default App;
