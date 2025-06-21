import React from "react";
import { Link } from "react-router-dom";

const Sidebar = () => {
  const menuItems = [
    { label: "My Account", icon: "pi pi-calendar", to: "/my-account" },
    { label: "Transfer", icon: "pi pi-wallet", to: "/transfer" },
    { label: "Loan", icon: "pi pi-money-bill", to: "/loan" },
    { label: "Report", icon: "pi pi-file", to: "/report" },
    { label: "Profile", icon: "pi pi-user", to: "/profile" },
    { label: "Service", icon: "pi pi-headphones", to: "/service" },
    { label: "Contact", icon: "pi pi-phone", to: "/contact" },
    { label: "Follow Me", icon: "pi pi-link", to: "/follow" },
  ];

  return (
    <div
      className="d-flex flex-column text-white h-100"
      style={{
        width: "230px",
        background: "linear-gradient(to bottom, #d4145a, #651fff)",
        paddingTop: "1rem",
      }}
    >
      <div className="text-center fw-bold fs-5 mb-4">
        <i className="pi pi-building me-2" />
        MB
      </div>
      <ul className="list-unstyled ps-3 pe-2">
        {menuItems.map((item, index) => (
          <li key={index} className="mb-3">
            <Link
              to={item.to}
              className="text-white text-decoration-none d-flex align-items-center"
              style={{ fontSize: "16px" }}
            >
              <i className={`${item.icon} me-2`} />
              {item.label}
            </Link>
          </li>
        ))}
      </ul>
    </div>
  );
};

export default Sidebar;