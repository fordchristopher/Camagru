import React from 'react';
import './login.css';

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
			[e.target.name] : e.target.value
		});
	}

	render() {
		return (
	<div className='container-login'>
		<div className="wrapper">
			<form className="form-signin shadow">
				<h2 className="form-signin-heading">Update Account Information</h2>
				<input type="text" onChange={this.handleChange} className="form-control" name="email" placeholder="Email Address"/>
				<br />
				<input type="password" onChange={this.handleChange} className="form-control" name="password" placeholder="Password"/>
				<br />
				<input type="name" onChange={this.handleChange} className="form-control" name="name" placeholder="username"/>
				<br />
				<button type='button' className="button-primary" onClick={() => this.props.login(this.state.username, this.state.password)}>Apply</button>
			</form>
		</div>
	</div>
		)
	}
}

export default Account;
