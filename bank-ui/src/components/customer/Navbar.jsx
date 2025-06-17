import { useState } from "react";
import { useNavigate } from "react-router-dom";
import '../../../src/css/customer.css'
import { useDispatch, useSelector } from "react-redux";
import { setUserDetails } from "../../store/actions/UserAction";

function Navbar() {
    const [user,] = useState(useSelector(state => state.user));
    console.log(user)
    const navigate = useNavigate()
    const dispatch = useDispatch()
    const logout = () => {
        let u = {
            'username': "",
            'role': ""
        }
        setUserDetails(dispatch)(u)
        localStorage.clear();
        navigate("/")
    }
    return (
        <div className="navbar navbar-expand-lg px-4 py-2 d-flex justify-content-between align-items-center mb-0 border-0 shadow-none">
            {/* Logo */}
            <div className="navbar-brand fw-bold">
                {/* <img src=".\images\logo-transparent.png" className="nav-logo" /> */}
                🏦 <label className="nav-title">Maverick Bank</label>
            </div>

            {/* Navigation Links */}
            <div className="d-flex gap-4 nav-title">
                <a className="nav-link" href="#home">Home</a>
                <a className="nav-link" href="#account">Account</a>
                <a className="nav-link" href="#transaction">Transaction</a>
                <a className="nav-link" href="#loan">Loan</a>
            </div>

            {/* Profile + Welcome + Logout */}
            <div className="d-flex align-items-center gap-3 profile">
                <span className="">Welcome, <strong>{user.username}</strong></span>

                <div className="dropdown">
                    <img
                        src="https://cdn-icons-png.flaticon.com/512/149/149071.png"
                        width="35"
                        height="35"
                        alt="profile"
                        className="rounded-circle dropdown-toggle"
                        data-bs-toggle="dropdown"
                        style={{ cursor: 'pointer' }}
                    />
                    <ul className="dropdown-menu dropdown-menu-end">
                        <li><a className="dropdown-item" href="#profile">Profile Management</a></li>
                        <li><hr className="dropdown-divider" /></li>
                        <li><button className="dropdown-item" onClick={()=>logout()}>Logout</button></li>
                    </ul>
                </div>
            </div>
        </div>
    )
}

export default Navbar; 