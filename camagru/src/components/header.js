import React from "react";
import { Link } from "react-router-dom"
import "./header.css";
import home from "./pictures/home.png";
import settings from "./pictures/account.png";
import camera from "./pictures/camera.png";

const Header = props => {
  if (props.user !== null) {
    return (
      <div className="container-head">
        <div className="container-icon">
          <Link to={`/home`} >
            <img src={home} className="icon" alt="home" />
          </Link>
        </div>
        <div className="container-icon small-top-margin">
          <Link to={`/`}>
            <img src={settings} className="icon" alt="home" />
          </Link>
        </div>
        <div className="container-icon top-margin">
          <Link to={`/create`}>
            <img src={camera} className="icon" alt="home" />
          </Link>
        </div>
        <h1
          onClick={props.showme}
          className="text-primary text-header text-center"
        >
          Camagru.
        </h1>
      </div>
    );
  } else {
    return (
      <div className="container-head">
        <div className="container-icon">
          <Link to={`/home`}>
            <img src={home} className="icon" alt="home" />
          </Link>
        </div>
        <div className="container-icon small-top-margin">
          <Link to={`/`}>
            <img src={settings} className="icon" alt="home" />
          </Link>
        </div>
        <h1
          onClick={props.showme}
          className="text-primary text-header text-center"
        >
          Camagru.
        </h1>
      </div>
    );
  }
};

export default Header;
