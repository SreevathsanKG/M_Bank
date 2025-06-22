import React, { useEffect, useState } from "react";
import { BreadCrumb } from "primereact/breadcrumb";
import { Button } from "primereact/button";
import { useSelector } from "react-redux";
import { useNavigate } from "react-router-dom";
import axios from "axios";
import { InputText } from "primereact/inputtext";
import { RadioButton } from "primereact/radiobutton";
import "primereact/resources/themes/lara-light-blue/theme.css";
import "primereact/resources/primereact.min.css";
import "primeicons/primeicons.css";

function CustomerProfile() {
    const navigate = useNavigate();

    const breadcrumbItems = [
        { label: "Profile", command: () => navigate("/customer/profile") }
    ];
    const home = { icon: "pi pi-home", command: () => navigate("/customer") };

    const [userData, setUserData] = useState({
        firstName: "",
        lastName: "",
        birthday: "",
        gender: "",
        email: "",
        phoneNumber: "",
        address: ""
    });
    useEffect(() => {
        const fetchUserDetails = async () => {
            try {
                const res = await axios.get("http://localhost:8080/api/customer/get", {
                    headers: {
                        Authorization: "Bearer " + localStorage.getItem("token")
                    }
                });
                setUserData(res.data);
            } catch (err) {
                console.error(err);
            }
        };
        fetchUserDetails();
    }, []);

    const [editMode, setEditMode] = useState({});
    const [isUpdated, setIsUpdated] = useState(false);
    const [msg, setMsg] = useState("");

    const handleFieldChange = (field, value) => {
        setUserData((u) => ({ ...u, [field]: value }));
        setIsUpdated(true);
    };

    const toggleEdit = (field) => {
        setEditMode((u) => ({ ...u, [field]: !u[field] }));
    };

    const putCustomer = async () => {
        try {
            await axios.put("http://localhost:8080/api/customer/put", userData, {
                headers: {
                    Authorization: "Bearer " + localStorage.getItem("token")
                }
            });
            setMsg("Profile updated successfully.");
            setIsUpdated(false);
            setEditMode({});
        } catch (error) {
            console.error(error);
            setMsg("Failed to update profile.");
        }
    };

    const renderField = (label, fieldName, type = "text") => (
        <div className="col-md-6 mb-3">
            <label className="form-label fw-bold">{label}</label>
            <div className="d-flex align-items-center">
                {editMode[fieldName] ? (
                    <InputText
                        type={type}
                        className="form-control me-2"
                        value={userData[fieldName] || ""}
                        onChange={(e) => handleFieldChange(fieldName, e.target.value)}
                    />
                ) : (
                    <span className="me-2">{userData[fieldName]}</span>
                )}
                <i
                    className="pi pi-pencil text-primary"
                    style={{ cursor: "pointer" }}
                    onClick={() => toggleEdit(fieldName)}
                ></i>
            </div>
        </div>
    );

    return (
        <div className="container-fluid py-4">
            <div className="row mb-2">
                <div className="col-12 col-lg-10 offset-lg-1">
                    <BreadCrumb model={breadcrumbItems} home={home} />
                </div>
            </div>

            <div className="row justify-content-center">
                <div className="col-12 col-lg-10">
                    <div className="card custom-card">
                        <div className="card-body">
                            <div className="text-center title-manage mb-1">
                                <h2>Customer Profile</h2>
                            </div>

                            {msg && <div className="alert alert-info">{msg}</div>}

                            <form className="row g-3">
                                {renderField("First Name", "firstName")}
                                {renderField("Last Name", "lastName")}
                                {renderField("Birthday", "dateOfBirth", "date")}
                                <div className="col-md-6">
                                    <label className="form-label fw-bold">Gender</label>
                                    {editMode["gender"] ? (
                                        <div className="d-flex">
                                            {["MALE", "FEMALE", "OTHER"].map((option) => (
                                                <div key={option} className="form-check me-3">
                                                    <RadioButton
                                                        inputId={option}
                                                        name="gender"
                                                        value={option}
                                                        onChange={(e) => handleFieldChange("gender", e.value)}
                                                        checked={userData.gender === option}
                                                    />
                                                    <label htmlFor={option} className="ms-2">
                                                        {option}
                                                    </label>
                                                </div>
                                            ))}
                                        </div>
                                    ) : (
                                        <div className="d-flex align-items-center">
                                            <span className="me-2">{userData.gender}</span>
                                            <i
                                                className="pi pi-pencil text-primary"
                                                style={{ cursor: "pointer" }}
                                                onClick={() => toggleEdit("gender")}
                                            ></i>
                                        </div>
                                    )}
                                </div>
                                {renderField("Email", "email", "email")}
                                {renderField("Phone Number", "phoneNumber", "tel")}
                                {renderField("Address", "address")}
                            </form>

                            {isUpdated && (
                                <div className="text-end">
                                    <Button label="Update" icon="pi pi-check" onClick={putCustomer} />
                                </div>
                            )}
                        </div>
                    </div>
                </div>
            </div>
        </div>
    );
}

export default CustomerProfile;
