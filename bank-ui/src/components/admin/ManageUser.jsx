import { useState } from "react";

function ManageUser() {

    const [searchTerm, setSearchTerm] = useState("");

    const users = [
        { id: 1, first: "Mark", last: "Otto", handle: "@mdo" },
        { id: 2, first: "Jacob", last: "Thornton", handle: "@fat" },
        { id: 3, first: "John", last: "Doe", handle: "@social" },
    ];

    const filteredUsers = users.filter(user =>
        `${user.first} ${user.last} ${user.handle}`
            .toLowerCase()
            .includes(searchTerm.toLowerCase())
    );

    return (
        <div className="container mt-5">
            <div className="row">
                <div className="col-md-12 text-center">
                    <h1>Manage User</h1>
                </div>
                <div className="col-md-12">
                    <div className="card mb-3 p-3 d-flex flex-row justify-content-between align-items-center">
                        <div class="dropdown">
                            <button class="btn btn-secondary dropdown-toggle" type="button" data-bs-toggle="dropdown" aria-expanded="false">
                                Dropdown button
                            </button>
                            <ul class="dropdown-menu">
                                <li><a class="dropdown-item" href="#">Action</a></li>
                                <li><a class="dropdown-item" href="#">Another action</a></li>
                                <li><a class="dropdown-item" href="#">Something else here</a></li>
                            </ul>
                        </div>
                        <div>
                            <input
                                type="text"
                                className="form-control"
                                placeholder="Search users..."
                                value={searchTerm}
                                onChange={(e) => setSearchTerm(e.target.value)}
                            />
                        </div>
                    </div>
                    <div className="card">
                        <table className="table">
                            <thead>
                                <tr>
                                    <th scope="col">#</th>
                                    <th scope="col">First</th>
                                    <th scope="col">Last</th>
                                    <th scope="col">Handle</th>
                                </tr>
                            </thead>
                            <tbody>
                                {filteredUsers.map(user => (
                                    <tr key={user.id}>
                                        <th scope="row">{user.id}</th>
                                        <td>{user.first}</td>
                                        <td>{user.last}</td>
                                        <td>{user.handle}</td>
                                    </tr>
                                ))}
                                {filteredUsers.length === 0 && (
                                    <tr>
                                        <td colSpan="4" className="text-center">No users found</td>
                                    </tr>
                                )}
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
        </div>
    )
}

export default ManageUser