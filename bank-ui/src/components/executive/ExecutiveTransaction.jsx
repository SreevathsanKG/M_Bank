import { useState, useEffect } from "react";
import { BreadCrumb } from "primereact/breadcrumb";
import { Button } from "primereact/button";
import { DataTable } from "primereact/datatable";
import { Column } from "primereact/column";
import { Calendar } from "primereact/calendar";
import axios from "axios";
import { useNavigate } from "react-router-dom";

function ExecutiveTransaction() {
    const navigate = useNavigate();
    const branchId = localStorage.getItem("branchId");

    const [transactions, setTransactions] = useState([]);
    const [fromDate, setFromDate] = useState(null);
    const [toDate, setToDate] = useState(null);
    const [searchTerm, setSearchTerm] = useState("");
    const [first, setFirst] = useState(0);
    const [rows, setRows] = useState(8);

    const breadcrumbItems = [
        { label: "Transactions" }
    ];
    const home = { icon: "pi pi-home", command: () => navigate("/executive") };

    const formatDate = (date) => date?.toISOString().split("T")[0];

    const fetchBetweenDates = async () => {
        if (!fromDate || !toDate) {
            alert("Please select both From and To dates.");
            return;
        }

        try {
            const res = await axios.get(`http://localhost:8080/api/transaction/get-btw/branchId/${branchId}`, {
                params: {
                    fromDate: formatDate(fromDate),
                    tillDate: formatDate(toDate)
                }, headers: {
                    Authorization: "Bearer " + localStorage.getItem("token")
                }
            });
            setTransactions(res.data);
        } catch (err) {
            console.error("Error fetching transactions between dates", err);
        }
    };

    const fetchFromDate = async () => {
        if (!fromDate) {
            alert("Please select From date.");
            return;
        }

        try {
            const res = await axios.get(`http://localhost:8080/api/transaction/get-from/branchId/${branchId}`, {
                params: {
                    fromDate: formatDate(fromDate)
                }, headers: {
                    Authorization: "Bearer " + localStorage.getItem("token")
                }
            });
            setTransactions(res.data);
        } catch (err) {
            console.error("Error fetching transactions from date", err);
        }
    };

    const filteredTransactions = transactions.filter(txn =>
        `${txn.accountId} ${txn.transactionType} ${txn.entryType}`.toLowerCase().includes(searchTerm.toLowerCase())
    );

    const onPageChange = (e) => {
        setFirst(e.first);
        setRows(e.rows);
    };

    return (
        <div className="container mt-4">
            <BreadCrumb model={breadcrumbItems} home={home} />

            {/* Date Filter Section */}
            <div className="row my-3">
                <div className="col-md-3">
                    <Calendar value={fromDate} onChange={(e) => setFromDate(e.value)} placeholder="From Date" showIcon />
                </div>
                <div className="col-md-3">
                    <Calendar value={toDate} onChange={(e) => setToDate(e.value)} placeholder="To Date" showIcon />
                </div>
                <div className="col-md-6 d-flex flex-wrap gap-2">
                    <Button label="Filter Between Dates" icon="pi pi-filter" onClick={fetchBetweenDates} />
                    <Button label="Filter From Date" icon="pi pi-calendar" onClick={fetchFromDate} />
                </div>
            </div>

            {/* Search Box */}
            <div className="row mb-2">
                <div className="col-md-6">
                    <input
                        type="text"
                        className="form-control"
                        placeholder="Search ...."
                        value={searchTerm}
                        onChange={(e) => setSearchTerm(e.target.value)}
                    />
                </div>
            </div>

            {/* Transactions Table */}
            <DataTable
                value={filteredTransactions}
                paginator
                rows={rows}
                first={first}
                onPage={onPageChange}
                showGridlines
                stripedRows
                tableStyle={{ minWidth: "30rem" }}
            >
                <Column field="account.id" header="Account ID" style={{ width: '15%', textAlign: 'center' }} sortable />
                <Column field="transactionType" header="Type" style={{ width: '20%', textAlign: 'center' }} />
                <Column field="transactionDate" header="Date" style={{ width: '15%', textAlign: 'center' }} sortable />
                <Column field="amount" header="Amount" body={(row) => `₹${row.amount?.toFixed(2)}`} style={{ width: '20%', textAlign: 'center' }} sortable />
                <Column field="entryType" header="Entry" style={{ width: '15%', textAlign: 'center' }} />
                <Column field="transferAccountId" header="Transfer A/C ID" style={{ width: '15%', textAlign: 'center' }} sortable />
            </DataTable>
        </div>
    );
}

export default ExecutiveTransaction;
