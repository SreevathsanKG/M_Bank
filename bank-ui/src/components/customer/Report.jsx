import { useEffect, useState } from "react";
import { BreadCrumb } from "primereact/breadcrumb";
import { Button } from "primereact/button";
import { DataTable } from "primereact/datatable";
import { Column } from "primereact/column";
import { Calendar } from "primereact/calendar";
import axios from "axios";
import { Dialog } from 'primereact/dialog';
import { useNavigate } from "react-router-dom";
import { Dropdown } from 'primereact/dropdown';

function Report() {
    const navigate = useNavigate();

    const [transactions, setTransactions] = useState([]);
    const [statementDto, setStatementDto] = useState(null);
    const [isStatement, setIsStatement] = useState(false);
    const [showStatementModal, setShowStatementModal] = useState(false);

    const [fromDate, setFromDate] = useState(null);
    const [toDate, setToDate] = useState(null);
    const [searchTerm, setSearchTerm] = useState("");

    const [first, setFirst] = useState(0);
    const [rows, setRows] = useState(5);

    const [accounts, setAccounts] = useState([]);
    const [selectedAccount, setSelectedAccount] = useState(null);

    const breadcrumbItems = [{ label: "Transaction Report" }];
    const home = { icon: "pi pi-home", command: () => navigate("/customer") };

    useEffect(() => {
        const fetchAccounts = async () => {
            try {
                const res = await axios.get("http://localhost:8080/api/account/get-one", {
                    headers: { Authorization: "Bearer " + localStorage.getItem("token") },
                });
                setAccounts(res.data);
            } catch (err) {
                console.error("Failed to fetch accounts", err);
            }
        };
        fetchAccounts();
    }, []);

    const formatDate = (date) => date?.toISOString().split("T")[0];

    const fetchLast10Transactions = async () => {
        try {
            const res = await axios.get(`http://localhost:8080/api/transaction/get-10/${selectedAccount.id}`, {
                headers: { Authorization: "Bearer " + localStorage.getItem("token") },
            });
            setTransactions(res.data);
            setIsStatement(false);
        } catch (error) {
            console.error("Error fetching last 10 transactions:", error);
        }
    };

    const fetchBetweenDates = async () => {
        if (!fromDate || !toDate) return alert("Select both from and to dates");

        try {
            const res = await axios.get(`http://localhost:8080/api/transaction/get-btw/${selectedAccount.id}`, {
                headers: { Authorization: "Bearer " + localStorage.getItem("token") },
                params: { fromDate: formatDate(fromDate), tillDate: formatDate(toDate) },
            });
            setTransactions(res.data);
            setIsStatement(false);
        } catch (error) {
            console.error("Error fetching between-date transactions:", error);
        }
    };

    const fetchFromDate = async () => {
        if (!fromDate) return alert("Select a from date");

        try {
            const res = await axios.get(`http://localhost:8080/api/transaction/get-from/${selectedAccount.id}`, {
                headers: { Authorization: "Bearer " + localStorage.getItem("token") },
                params: { fromDate: formatDate(fromDate) },
            });
            setTransactions(res.data);
            setIsStatement(false);
        } catch (error) {
            console.error("Error fetching from-date transactions:", error);
        }
    };

    const fetchStatement = async () => {
        if (!fromDate || !toDate) return alert("Select both from and to dates");

        try {
            const res = await axios.get(`http://localhost:8080/api/transaction/get/statement/${selectedAccount.id}`, {
                headers: { Authorization: "Bearer " + localStorage.getItem("token") },
                params: {
                    fromDate: formatDate(fromDate),
                    tillDate: formatDate(toDate),
                },
            });
            setStatementDto(res.data);
            setShowStatementModal(true);
            setTransactions(res.data.statementListDtos);
            setIsStatement(true);
        } catch (error) {
            console.error("Error fetching statement:", error);
        }
    };

    const exportPdf = async () => {
        const jsPDF = (await import("jspdf")).default;
        const autoTable = (await import("jspdf-autotable")).default;

        const doc = new jsPDF();
        doc.setFontSize(16);
        doc.text("Maverick Bank", 105, 15, null, null, "center");

        doc.setFontSize(12);
        doc.text(`Account ID: ${statementDto.accountId}`, 15, 30);
        doc.text(`Name: ${statementDto.name}`, 120, 30);
        doc.text(`From Date: ${formatDate(fromDate)}`, 15, 40);
        doc.text(`To Date: ${formatDate(toDate)}`, 120, 40);
        doc.text(`Opening Balance: Rs.${statementDto.openingBalance.toFixed(2)}`, 15, 50);
        doc.text(`Closing Balance: Rs.${statementDto.closingBalance.toFixed(2)}`, 120, 50);

        const tableData = statementDto.statementListDtos.map(txn => [
            txn.transactionDate,
            txn.transactionType,
            `Rs.${txn.amount.toFixed(2)}`,
            txn.entryType,
            txn.description,
            `Rs.${txn.balanceAfterTxn.toFixed(2)}`
        ]);

        autoTable(doc, {
            startY: 60,
            head: [["Date", "Type", "Amount", "Entry", "Description", "Balance"]],
            body: tableData,
        });

        doc.save("Account_Statement.pdf");
    };

    const onPageChange = (event) => {
        setFirst(event.first);
        setRows(event.rows);
    };

    const filteredTransactions = transactions.filter((txn) =>
        `${txn.transactionType} ${txn.description} ${txn.entryType}`.toLowerCase().includes(searchTerm.toLowerCase())
    );

    return (
        <div className="container mt-4">
            <BreadCrumb model={breadcrumbItems} home={home} />

            {/* Filter Section */}
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
                    <Button label="Last 10" icon="pi pi-history" onClick={fetchLast10Transactions} />
                    <Button label="View Statement" icon="pi pi-file" onClick={fetchStatement} className="p-button-info" />
                    <Dropdown
                        value={selectedAccount}
                        onChange={(e) => {
                            setSelectedAccount(e.value);
                        }}
                        options={accounts.filter(acc => acc.status === "ACTIVE")}
                        optionLabel={(option) => `${option.id} - ${option.accountType.type}`}
                        placeholder="Select Account"
                        className="w-full md:w-20rem"
                    />
                </div>
            </div>

            <Dialog
                header="Account Statement"
                visible={showStatementModal}
                onHide={() => setShowStatementModal(false)}
                style={{ width: '60vw' }}
                breakpoints={{ '960px': '95vw' }}
                draggable={false}
            >
                {statementDto && (
                    <div className="p-3">
                        <h4 className="text-center mb-3">Maverick Bank</h4>
                        <div className="row mb-2">
                            <div className="col-md-6"><strong>Account ID:</strong> {statementDto.accountId}</div>
                            <div className="col-md-6"><strong>Name:</strong> {statementDto.name}</div>
                        </div>
                        <div className="row mb-2">
                            <div className="col-md-6"><strong>From:</strong> {formatDate(fromDate)}</div>
                            <div className="col-md-6"><strong>To:</strong> {formatDate(toDate)}</div>
                        </div>
                        <div className="row mb-3">
                            <div className="col-md-6"><strong>Opening Balance:</strong> ₹{statementDto.openingBalance.toFixed(2)}</div>
                            <div className="col-md-6"><strong>Closing Balance:</strong> ₹{statementDto.closingBalance.toFixed(2)}</div>
                        </div>
                        <div className="text-end">
                            <Button label="Download Statement PDF" icon="pi pi-download" className="p-button-success" onClick={exportPdf} />
                        </div>
                    </div>
                )}
            </Dialog>

            {/* Search Box */}
            <div className="row mb-2">
                <div className="col-md-6">
                    <input
                        type="text"
                        className="form-control"
                        placeholder="Search transactions..."
                        value={searchTerm}
                        onChange={(e) => setSearchTerm(e.target.value)}
                    />
                </div>
            </div>

            {/* Data Table */}
            <DataTable
                value={filteredTransactions}
                showGridlines
                stripedRows
                scrollable
                scrollHeight="400px"
                paginator
                rows={rows}
                first={first}
                onPage={onPageChange}
                paginatorTemplate="RowsPerPageDropdown FirstPageLink PrevPageLink PageLinks NextPageLink LastPageLink"
                tableStyle={{ minWidth: "30rem" }}
            >
                <Column field="transactionType" header="Type" />
                <Column field="transactionDate" header="Date" />
                <Column field="amount" header="Amount" body={(rowData) => `₹${rowData.amount?.toFixed(2)}`} />
                {!isStatement && <Column field="transferAccountId" header="Transfer A/C ID" />}
                <Column field="entryType" header="Entry" />
                <Column field="description" header="Description" />
                <Column field="balanceAfterTxn" header="Balance" body={(rowData) => `₹${rowData.balanceAfterTxn?.toFixed(2)}`} />
            </DataTable>
        </div>
    );
}

export default Report;
