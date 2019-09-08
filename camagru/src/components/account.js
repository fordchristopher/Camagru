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
			reload: false
		}
	}

	handleChange = (e) => {
		this.setState({
			[e.target.name]: e.target.value
		});
	}

	toggleNotification = () => {
		let temp = {};
		temp = Object.assign(temp, this.props.user);
		temp.receive_notifications = !temp.receive_notifications;
		console.log(this.props.user);
		this.props.updateUser(temp);

		let newPreference;
		if (this.props.receive_notifications === 1)
			newPreference = 0;
		else
			newPreference = 1;

		fetch(`${APIUrl}/users/updateNotificationPreference`, {
			method: 'post',
			headers: {
				'Origin': baseURL,
				'Access-Control-Request-Method': 'POST',
				'Content-Type': 'application/json'
			},
			body: JSON.stringify({
				id: this.props.user.id,
				password: this.props.user.password,
				receive_notifications: newPreference
			})
		})
			.then(res => res.json())
			.then(data => {
				alert(data.response);
			});
			this.setState({
				reload: !(this.state.reload)
			})
	}

	renderButton = () => {
		console.log(this.props.user);
		if (this.props.user.receive_notifications)
		{
			return (
				<button type="button" onClick={this.toggleNotification}>Turn off Email Notifications</button>
			);
		}
		else
		{
			return (
				<button type="button" onClick={this.toggleNotification}>Turn on Email Notifications</button>
			);
		}
	}

	editAccount = () => {
		let logout = false;
		if (this.state.email.length > 0) {
			if (validateEmail(this.state.email) === false) {
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
						'password' : this.props.user.password,
						'id': this.props.user.id
					})
				})
				.then(response => response.json())
				.then(data => alert(data.response));
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
					password : this.props.user.password,
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
						'new_password': this.state.password,
						'old_password' : this.props.user.password,
						'id': this.props.user.id
					})
				})
			}
		}
		if (logout)
			this.props.logout();
		this.setState({
			email: "",
			password: "",
			username: "",
			reload: false
		});
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
							<br />
							{this.renderButton()}
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
