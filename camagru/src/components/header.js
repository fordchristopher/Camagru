import React from 'react';
import './header.css';

const Header = (props) => {
  return (
  	<div className="container-head">
	  <h1 onClick={props.showme} className="text-primary text-header text-center">
	  	Camagru.
	  </h1>
	</div>
  )
}

export default Header;
