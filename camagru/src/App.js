import React from 'react';
import Header from './components/header.js';
import Login from './components/login.js';
import './App.css';

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
		if (true) {
			this.setState({
				user: username
			});
		}
		else
			this.logout();
	}

	logout = (username, pass) => {
		this.setState({
			user: ""
		});
	}

	showme = () => {
		console.log(this.state);
	}

	render() {
		let	login;

		if (this.state.user == null)
			login = <Login login={this.login} />;
		else
			login = <div>logged in!</div>;
		return (
		<div className="App">
			<Header showme={this.showme} />
			{login}
		</div>
	  	);
	}
}

export default App;
