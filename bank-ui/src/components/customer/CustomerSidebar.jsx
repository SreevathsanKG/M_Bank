import { Link, useLocation } from "react-router-dom";

function CustomerSidebar({ setIsClosed, overlayRef, wrapperRef }) {
    const location = useLocation();

    const handleLinkClick = () => {
        setIsClosed(true); // close the sidebar
        if (overlayRef.current) overlayRef.current.style.display = 'none';
        if (wrapperRef.current) wrapperRef.current.classList.remove('toggled');
    };

    return (
        <div>
            <nav className="navbar navbar-inverse fixed-top" id="sidebar-wrapper" role="navigation">
                <ul className="nav sidebar-nav">
                    <div className="sidebar-header">
                        <div className="sidebar-brand">
                            <i className="bi bi-bank" style={{ marginRight: "15px" }}></i>
                            <span style={{ fontSize: "20px", color: "white", fontWeight: "bold" }}>MB</span>
                        </div>
                    </div>
                    <li>
                        <Link to="/customer" onClick={handleLinkClick}>
                            <i className="bi bi-safe" style={{ marginRight: "10px" }}></i>
                            My Account
                        </Link>
                    </li>
                    <li>
                        <Link to="/customer/transfer" onClick={handleLinkClick}>
                            <i className="bi bi-currency-exchange" style={{ marginRight: "10px" }}/>
                            Transfer
                        </Link>
                    </li>
                    <li>
                        <Link to="/customer/loan" onClick={handleLinkClick}>
                            <i className="bi bi-cash-stack" style={{ marginRight: "10px" }}/>
                            Loan
                        </Link>
                    </li>
                    <li>
                        <Link to="/customer/report" onClick={handleLinkClick}>
                            <i className="bi bi-file-earmark-text" style={{ marginRight: "10px" }}/>
                            Report
                        </Link>
                    </li>
                    <li>
                        <Link to="/customer/profile" onClick={handleLinkClick}>
                            <i className="bi bi-person" style={{ marginRight: "10px" }}/>
                            Profile
                        </Link>
                    </li>
                    <li>
                        <Link to="/#" onClick={handleLinkClick}>
                            <i className="bi bi-headset" style={{ marginRight: "10px" }}/>
                            Service
                        </Link>
                    </li>
                    <li>
                        <Link to="/#" onClick={handleLinkClick}>
                            <i className="bi bi-telephone" style={{ marginRight: "10px" }}/>
                            Contact
                        </Link>
                    </li>
                    <li>
                        <Link to="/#" onClick={handleLinkClick}>
                            <i className="bi bi-link-45deg" style={{ marginRight: "10px" }}/>
                            Follow Me
                        </Link>
                    </li>
                </ul>
            </nav>
        </div>
    );
}

export default CustomerSidebar;