import React from 'react';
import './header.css';

const Footer = (props) => {
	let logout;

	if (props.user)
		logout = <h1 class="text-secondary" onClick={props.logout}>Log out</h1>;
	else
		logout = false;
  return (
  	<div className="container-foot">
	  {logout}
	  <h2 onClick={props.showme} className="text-primary text-center">
	  	Camagru.
	  </h2>
	  <h6 class="text-secondary">Created by Chris</h6>
	</div>
  )
}

export default Footer;
