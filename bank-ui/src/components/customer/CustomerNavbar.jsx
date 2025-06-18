import { useState } from "react";
import { useNavigate } from "react-router-dom";
import '../../css/CustomerDashboard.css';
import { useDispatch, useSelector } from "react-redux";
import { setUserDetails } from "../../store/actions/UserAction";

function CustomerNavbar() {
    const [user,] = useState(useSelector(state => state.user));
    const navigate = useNavigate();
    const dispatch = useDispatch()
    const logout = () => {
        let u = {
            "username": "",
            "role": ""
        }
        setUserDetails(dispatch)(u)
        localStorage.clear();
        navigate("/")
    }
    return (
        <div >
            <nav className="navbar justify-content-between"  >
                <div className="d-flex align-items-center gap-4 "></div>
                <div className="d-flex align-items-center gap-4 "></div>
                <div className="d-flex align-items-center gap-4 profile">
                {/* <div className="form-inline mt-2 mb-4 ">
                    Welcome {name}
                    &nbsp;&nbsp;&nbsp;
                    <button class="btn btn-outline-success" onClick={() => logout()}>Logout</button>
                </div> */}
                <div></div>
                <span className="">Welcome, <strong>{user.username}</strong></span>
                <div className="dropdown me-2">
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
            </nav>

        </div>
    )
}

export default CustomerNavbar; 