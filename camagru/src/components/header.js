import React from "react";
import { baseURL } from "../global.js";
import "./header.css";
import home from "./pictures/home.png";
import settings from "./pictures/account.png";
import camera from "./pictures/camera.png";

const Header = props => {
  if (props.user) {
    return (
      <div className="container-head">
        <div className="container-icon">
          <a href={`${baseURL}/home`}>
            <img src={home} className="icon" alt="home" />
          </a>
        </div>
        <div className="container-icon small-top-margin">
          <a href={`${baseURL}/`}>
            <img src={settings} className="icon" alt="home" />
          </a>
        </div>
        <div className="container-icon top-margin">
          <a href={`${baseURL}/create`}>
            <img src={camera} className="icon" alt="home" />
          </a>
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
          <a href={`${baseURL}/home`}>
            <img src={home} className="icon" alt="home" />
          </a>
        </div>
        <div className="container-icon small-top-margin">
          <a href={`${baseURL}/`}>
            <img src={settings} className="icon" alt="home" />
          </a>
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
