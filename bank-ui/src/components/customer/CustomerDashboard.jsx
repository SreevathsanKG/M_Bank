import { Link, Outlet, useNavigate } from 'react-router-dom'
import '../../../src/css/customer.css'
import { useEffect, useRef, useState } from 'react'
import axios from 'axios'
import Navbar from './Navbar'



function CustomerDashboard() {

    const navigate = useNavigate()


    useEffect(() => {
        let token = localStorage.getItem('token');
        if (token == null || token == undefined || token == "")
            navigate("/")
    }, []);

    return (
        <div>
        <div className='container-fluid nav-bar'>
            <Navbar/>
        </div>
        <div className='container mt-5 bg-light'>
            <Outlet/>
        </div>
        </div>
    )
}

export default CustomerDashboard