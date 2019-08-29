import React from 'react';
import Login from './login.js';
import './login.css';
import { APIUrl, baseURL } from '../global.js';
import { validatePassword } from '../auxiliary.js';
import { validateEmail } from '../auxiliary.js';

class Account extends React.Component {
	constructor() {
		super();
		this.state = {
			email: "",
			password: "",
			username: "",
		}
	}

	handleChange = (e) => {
		this.setState({
			[e.target.name]: e.target.value
		});
	}

	editAccount = () => {
		let logout = false;
		if (this.state.email.length > 0) {
			if (validateEmail(this.state.email) == false) {
				alert("Please enter a valid email address");
			} else {
				logout = true;
				fetch(`${APIUrl}/users/updateEmail`, {
					method: 'post',
					headers: {
						'Origin': baseURL,
						'Access-Control-Request-Method': 'POST',
						'Content-Type': 'application/json'
					},
					body: JSON.stringify({
						'email': this.state.email,
						'id': this.props.user.id
					})
				});
			}
		}
		if (this.state.username.length > 0) {
			logout = true;
			fetch(`${APIUrl}/users/updateUsername`, {
				method: 'post',
				headers: {
					'Origin': baseURL,
					'Access-Control-Request-Method': 'POST',
					'Content-Type': 'application/json'
				},
				body: JSON.stringify({
					username: this.state.username,
					id: this.props.user.id
				})
			})
		}
		if (this.state.password.length > 0) {
			if (validatePassword(this.state.password) === false) {
				alert("Password must include an uppercase letter, a lowercase letter and a number");
			} else {
				logout = true;
				fetch(`${APIUrl}/users/updatePassword`, {
					method: 'post',
					headers: {
						'Origin': baseURL,
						'Access-Control-Request-Method': 'POST',
						'Content-Type': 'application/json'
					},
					body: JSON.stringify({
						'password': this.state.password,
						'id': this.props.user.id
					})
				})
			}
		}
		if (logout)
			this.props.logout();
	}

	render() {
		if (this.props.user !== null) {
			return (
				<div className='container-login'>
					<div className="wrapper">
						<form className="form-signin shadow">
							<h2 className="form-signin-heading">Update Account Information</h2>
							<input type="text" onChange={this.handleChange} className="form-control" name="email" placeholder="Email Address" />
							<br />
							<input type="password" onChange={this.handleChange} className="form-control" name="password" placeholder="Password" />
							<br />
							<input type="username" onChange={this.handleChange} className="form-control" name="username" placeholder="username" />
							<br />
							<button type='button' className="button-primary" onClick={this.editAccount}>Apply</button>
						</form>
					</div>
				</div>
			)
		}
		else
			return (<Login login={this.props.login} />)
	}
}

export default Account;
