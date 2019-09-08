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
      user: null,
      userLoaded: false
    };
    this.login = this.login.bind(this);
    this.logout = this.logout.bind(this);
    this.showme = this.showme.bind(this);
  }

  componentDidMount() {
    if (typeof localStorage.getItem('user') !== "undefined") {
      this.setState({
       user: JSON.parse(localStorage.getItem('user'))
      });
    } 
  }

  login = (username, pass) => {
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
      if (data.data && typeof(data.data.id) !== 'undefined') {
        this.setState({
          user: data.data
        }, () => {
          localStorage.setItem( 'user', JSON.stringify(data.data));
        })
      } else {
        alert(data.data);
      }
    });
  };

	updateUser = (newUser) => {
		this.setState({
			user : newUser
		})
	}

  logout = (username, pass) => {
    this.setState({
      user: null
    });
    if (localStorage.getItem('user') !== null)
      localStorage.removeItem('user');
      window.location.href = `${baseURL}`
  };

  showme = () => {
    console.log(this.state);
  };

  render() {
    return (
      <div className="App">
        <BrowserRouter>
          <Header showme={this.showme} user={this.state.user} />
          <Route
            path="/"
            render={() => <Account user={this.state.user}
            login={this.login}
            logout={this.logout}
            updateUser={this.updateUser} />}
            exact
          />
          <Route
            path="/create"
            render={() => <Stream user={this.state.user} />}
          />
           <Route
            path="/index"
            render={() => {window.location.href=`${baseURL}`}}
          />
          <Route
            path="/home"
            render={() => <Post user={this.state.user}/>}
          />
          <Footer user={this.state.user} logout={this.logout} />
        </BrowserRouter>
      </div>
    );
  }
}

export default App;
