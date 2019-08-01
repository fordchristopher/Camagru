import React from 'react';
import './header.css';
import home from './pictures/home.png';
import settings from './pictures/account.png';

const Header = (props) => {
  return (
  	<div className="container-head">
	  <div class="container-icon">
	  	<img src={home} class="icon" alt="home"/>
	  </div>
	  <div class="container-icon small-top-margin">
	  	<img src={settings} class="icon" alt="home"/>
	  </div>
	  <h1 onClick={props.showme} className="text-primary text-header text-center">
	  	Camagru.
	  </h1>
	</div>
  )
}

export default Header;
