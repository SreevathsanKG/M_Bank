import React from 'react';
import Navbar from './Navbar';
import { Outlet } from 'react-router-dom';
import Sidebar from './Sidebar';

const Dashboard = () => {
  const username = "ravi@gmail.com"; // can be taken from auth later

  return (
    <div className="d-flex flex-column vh-100">
      <Navbar username={username} />
      <div className="d-flex flex-grow-1">
        <Sidebar />
        <div className="flex-grow-1 p-4" style={{ background: 'linear-gradient(to right, #d4145a, #651fff)', color: 'white' }}>
          <Outlet />
        </div>
      </div>
    </div>
  );
};

export default Dashboard;