import { useState } from "react";
import { useDispatch, useSelector } from "react-redux";
import { DataTable } from "primereact/datatable";
import { Column } from "primereact/column";
import { Button } from "primereact/button";
import "primereact/resources/themes/lara-light-blue/theme.css";
import "primereact/resources/primereact.min.css";
import "primeicons/primeicons.css";
import '../../css/AdminDashboard.css';
import axios from "axios";
import { useNavigate, useLocation } from 'react-router-dom';

function ManageUser() {
    const userInfo = useSelector((state) => state.userInfo.userInfo);
    const [searchTerm, setSearchTerm] = useState("");
    const [first, setFirst] = useState(0); // State for pagination: first row index
    const [rows, setRows] = useState(5); // State for pagination: rows per page

    const dispatch = useDispatch()
    const navigate = useNavigate()

    const filteredUserInfo = userInfo.filter((user) => user.role !== "ADMIN")
            .filter((user) =>
                `${user.username} ${user.role} ${user.id} ${user.userStatus}`.toLowerCase().includes(searchTerm.toLowerCase())
            );

    const onPageChange = (event) => {
        setFirst(event.first);
        setRows(event.rows);
    };

    return (
       <div className="container-fluid py-4">
            <div className="row justify-content-center">
                <div className="col-12 col-lg-10">
                    <div className="card custom-card">
                        <div className="card-body">
                            <div className="text-center mb-2 title-manage">
                                <h1>User Management</h1>
                                <div>Add, Edit and Manage User</div>
                            </div>

                            <div className="card mb-3 p-1">
                                <div className="d-flex gap-3 align-items-center" style={{ width: '100%' }}>
                                    <div className="w-50">
                                        <Button
                                            type="button"
                                            icon="pi pi-user-plus"
                                            label="Add New User"
                                            className="w-100 soft-button"
                                            onClick={() => navigate('/admin/addUser')}
                                        />
                                    </div>
                                    <div className="w-50">
                                        <input
                                            type="text"
                                            className="form-control"
                                            placeholder="Search users..."
                                            value={searchTerm}
                                            onChange={(e) => setSearchTerm(e.target.value)}
                                        />
                                    </div>
                                </div>
                            </div>

                            {/* This div is crucial for flex growth and containing the DataTable */}
                            <div className="table-data-wrapper">
                                <DataTable
                                    value={filteredUserInfo}
                                    showGridlines
                                    stripedRows
                                    tableStyle={{ minWidth: "30rem" }}
                                    className="custom-table"
                                    scrollable // Enable scrolling for the table body
                                    scrollHeight="flex" // Make the table body fill available height and scroll
                                    paginator // Enable paginator
                                    rows={rows} // Number of rows per page
                                    first={first} // Index of the first row
                                    onPage={onPageChange} // Pagination event handler
                                    paginatorTemplate="RowsPerPageDropdown FirstPageLink PrevPageLink PageLinks NextPageLink LastPageLink"
                                >
                                    <Column field="id" header="User ID" style={{ width: "20%" }} />
                                    <Column field="username" header="Username" style={{ width: "30%" }} />
                                    <Column field="role" header="Role" style={{ width: "20%" }} />
                                    <Column field="userStatus" header="Status" style={{ width: "20%" }} />
                                    <Column
                                        header="Action"
                                        style={{ width: "10%", textAlign: 'center' }}
                                        body={(s) => (
                                            <Button
                                                icon="pi pi-pencil"
                                                className="p-button-sm p-button-text p-button-rounded"
                                                title={`Click to ${s.userStatus === "ACTIVE" ? "deactivate" : "activate"} user`}
                                                onClick={() => navigate(`/admin/putUserStatus/${s.id}/${s.userStatus}`)}
                                            />
                                        )}
                                    />
                                </DataTable>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    );
}

export default ManageUser