import React from 'react';
import './login.css';

class Account extends React.Component {
	constructor() {
		super();
		this.state = {
			username: "",
			password: "",
			new_email: "",
			new_pass: "",
			new_name: "",
		}
	}

	handleChange = (e) => {
		this.setState({
			[e.target.name] : e.target.value
		});
	}

	render() {
		return (
	<div className='container-login'>
		<div className="wrapper">
			<form className="form-signin shadow">
				<h2 className="form-signin-heading">Login</h2>
				<input type="text" onChange={this.handleChange} className="form-control" name="username" placeholder="Email Address"/>
				<input type="password" onChange={this.handleChange} className="form-control" name="password" placeholder="Password"/>
				<button type='button' className="button-primary" onClick={() => this.props.login(this.state.username, this.state.password)}>Login</button>
			</form>
			<br />
			<hr />
			<br />
			<form className="form-signin shadow">
				<h2 className="form-signin-heading">Register</h2>
				<div className="block">
					<input type="text" onChange={this.handleChange} className="form-control" name="new_email" placeholder="Email Address"/>
				</div>
				<div className="block">
					<input type="password" onChange={this.handleChange} className="form-control" name="new_pass" placeholder="Password"/>
				</div>
				<div className="block">
					<input type="name" onChange={this.handleChange} className="form-control" name="new_name" placeholder="Name" />
				</div>
			<br />
			<button type='button' className="button-primary">Create Account</button>
			</form>
		</div>
	</div>
		)
	}
}

export default Account;
