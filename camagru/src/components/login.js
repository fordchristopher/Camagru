import React from 'react';
import './login.css';
import { APIUrl, baseURL } from '../global.js';
import { validatePassword } from '../auxiliary.js';
import { validateEmail } from '../auxiliary.js';

class Login extends React.Component {
	constructor() {
		super();
		this.state = {
			email: "",
			password: "",
			new_email: "",
			new_pass: "",
			new_name: "",
			forgotten: false
		}
	}

	handleChange = (e) => {
		this.setState({
			[e.target.name] : e.target.value
		});
	}

	forgotPassword = () => {
		let email = window.prompt("Please enter your email address");
		//Need to validate the email address
		if (email !== "")
			alert(`Instructions have been sent to ${email} if there is an account associated with it`);
		else
			alert("Please enter a valid email address");
	}

	register = () => {
		if (validatePassword(this.state.new_pass) === false)
		{
			alert("Password must include an uppercase letter, a lowercase letter and a number");
			return ;
		}
		if (validateEmail(this.state.new_email) === false)
		{
			alert("Please enter a valid email adress");
			return ;
		}
		let data = {
			method: "post",
			headers: {
				'Origin': baseURL,
				'Access-Control-Request-Method': 'POST',
				'Content-Type': 'application/json'
			},
			body: JSON.stringify({
				email: this.state.new_email,
				username: this.state.new_name,
				password: this.state.new_pass
			})
		}
		fetch(`${APIUrl}/users/create`, data);
	}

	render() {
		return (
			<div className='container-login'>
				<div className="wrapper">
					<form className="form-signin shadow">
						<h2 className="form-signin-heading">Login</h2>
						<input type="text" onChange={this.handleChange} className="form-control" name="email" placeholder="Email Address" />
						<input type="password" onChange={this.handleChange} className="form-control" name="password" placeholder="Password" />
						<button type='button' className="button-primary" onClick={() => this.props.login(this.state.email, this.state.password)}>Login</button>
						<br />
						<button type='button' className="button-primary" onClick={this.forgotPassword}>Forgot password</button>
					</form>
					<br />
					<hr />
					<br />
					<form className="form-signin shadow">
						<h2 className="form-signin-heading">Register</h2>
						<div className="block">
							<input type="text" onChange={this.handleChange} className="form-control" name="new_email" placeholder="Email Address" />
						</div>
						<div className="block">
							<input type="password" onChange={this.handleChange} className="form-control" name="new_pass" placeholder="Password" />
						</div>
						<div className="block">
							<input type="name" onChange={this.handleChange} className="form-control" name="new_name" placeholder="Name" />
						</div>
						<br />
						<button onClick={this.register} type='button' className="button-primary">Create Account</button>
					</form>
				</div>
			</div>
		)
	}
}

export default Login;
