import React, { useEffect, useState } from "react";
import { BreadCrumb } from "primereact/breadcrumb";
import { Button } from "primereact/button";
import { DataTable } from "primereact/datatable";
import { Column } from "primereact/column";
import { Dialog } from "primereact/dialog"; // 🆕 For the popup
import { useNavigate } from "react-router-dom";
import axios from "axios";

function MyAccount() {
    const navigate = useNavigate();
    const [account, setAccount] = useState([]);
    const [error, setError] = useState("");

    const [showTxnDialog, setShowTxnDialog] = useState(false); // 🆕
    const [txnData, setTxnData] = useState([]); // 🆕

    const breadcrumbItems = [
        { label: "My Account", command: () => navigate("/customer") }
    ];
    const home = { icon: "pi pi-home", command: () => navigate("/customer") };

    useEffect(() => {
        const fetchAccount = async () => {
            try {
                const res = await axios.get("http://localhost:8080/api/account/get-one", {
                    headers: { Authorization: "Bearer " + localStorage.getItem("token") }
                });
                if (res.data.length === 0) {
                    setError("You don't have an account. Create one to get started.");
                    return;
                }

                const fetchData = res.data.map(acc => ({
                    id: acc.id,
                    type: acc.accountType?.type,
                    ifscCode: acc.branch?.ifscCode,
                    branchName: acc.branch?.branchName,
                    status: acc.status,
                    balance: acc.balance,
                }));

                setAccount(fetchData);
            } catch (err) {
                if (err.response && err.response.data.message === "Customer has no Account") {
                    setError("You don't have an account. Create one to get started.");
                } else {
                    setError("Failed to load account.");
                }
            }
        };

        fetchAccount();
    }, []);

    const handleLast10Txn = async (accountId) => {
        try {
            const res = await axios.get(`http://localhost:8080/api/transaction/get-10/${accountId}`, {
                headers: { Authorization: "Bearer " + localStorage.getItem("token") }
            });
            setTxnData(res.data);
            setShowTxnDialog(true);
        } catch (error) {
            console.error("Error fetching transactions:", error);
        }
    };

    return (
        <div className="container mt-4">
            <BreadCrumb model={breadcrumbItems} home={home} />
            <div className="card mt-4">
                <div className="card-body">
                    <h2 className="mt-3 fw-bold text-center mb-4 title-account">My Account</h2>

                    {error && (
                        <div className="alert alert-warning mt-3">
                            {error}{" "}
                            <Button
                                label="Create Account"
                                icon="pi pi-plus"
                                className="ms-2"
                                onClick={() => navigate("/customer/account/create")}
                            />
                        </div>
                    )}

                    {account.length > 0 && (
                        <div>
                            <DataTable value={account} className="mt-4">
                                <Column field="id" header="Account ID" />
                                <Column field="type" header="Account Type" />
                                <Column field="ifscCode" header="IFSC Code" />
                                <Column field="branchName" header="Branch Name" />
                                <Column field="status" header="Status" />
                                <Column header="Balance" body={(rowData) => `₹${rowData.balance.toFixed(2)}`} />
                                
                                <Column
                                    header="Last 10 Txns"
                                    body={(rowData) => (
                                        <Button
                                            label="View"
                                            icon="pi pi-eye"
                                            className="p-button-sm"
                                            onClick={() => handleLast10Txn(rowData.id)}
                                        />
                                    )}
                                />
                            </DataTable>

                            <div className="text-end mt-3">
                                <Button label="Create Another Account" icon="pi pi-plus" onClick={() => navigate("/customer/account/create")} />
                            </div>
                        </div>
                    )}

                    {/* 🆕 Transactions Popup */}
                    <Dialog
                        header="Last 10 Transactions"
                        visible={showTxnDialog}
                        onHide={() => setShowTxnDialog(false)}
                        style={{ width: '60vw' }}
                        breakpoints={{ '960px': '95vw' }}
                    >
                        {txnData.length > 0 ? (
                            <DataTable value={txnData}>
                                <Column field="transactionDate" header="Date" />
                                <Column field="transactionType" header="Type" />
                                <Column field="amount" header="Amount" body={(rowData) => `₹${rowData.amount.toFixed(2)}`} />
                                <Column field="entryType" header="Entry" />
                                <Column field="description" header="Description" />
                                <Column field="balanceAfterTxn" header="Balance After" body={(rowData) => `₹${rowData.balanceAfterTxn.toFixed(2)}`} />
                            </DataTable>
                        ) : (
                            <div>No transactions found.</div>
                        )}
                    </Dialog>
                </div>
            </div>
        </div>
    );
}

export default MyAccount;
