import React from "react";
import { Avatar } from "primereact/avatar";

const Navbar = ({ username }) => {
  return (
    <nav className="d-flex justify-content-between align-items-center px-4 py-2 shadow bg-white border-bottom">
      <div className="d-flex align-items-center">
        <i className="pi pi-bars fs-4 me-3" />
        <img
          src="/logo-home-transaprent.png"
          alt="Logo"
          style={{ width: "32px", height: "32px" }}
          className="me-2"
        />
        <span className="fw-bold fs-5">MAVERICK BANK</span>
      </div>

      <div className="d-flex align-items-center">
        <span className="me-3 text-danger fw-semibold">
          Welcome, <span className="text-dark">{username}</span>
        </span>
        <Avatar icon="pi pi-user" className="me-2" shape="circle" />
      </div>
    </nav>
  );
};

export default Navbar;