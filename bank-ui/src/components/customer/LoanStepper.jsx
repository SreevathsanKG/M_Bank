import { useState } from "react";
import { CheckCircle, Circle, Dot } from 'react-bootstrap-icons';
// import '../../../src/css/customer.css'

function LoanStepper({ currentStep }) {
  const steps = [
    "Select Loan Type",
    "Enter Loan Details",
    "Document Upload"
  ];

  return (
    <div className="d-flex flex-column align-items-start gap-3 px-3 py-4">
      {steps.map((label, index) => (
        <div key={index} className="d-flex align-items-start">
          <div className="d-flex flex-column align-items-center me-3">
            <div className={`rounded-circle d-flex justify-content-center align-items-center ${currentStep === index ? 'bg-primary text-white' : currentStep > index ? 'bg-success text-white' : 'bg-light text-dark'}`}
              style={{ width: '30px', height: '30px', fontWeight: 'bold' }}>
              {currentStep > index ? <CheckCircle size={18} /> : index + 1}
            </div>
            {index !== steps.length - 1 && (
              <div style={{ height: '40px', width: '2px', backgroundColor: '#ccc', marginTop: '5px' }}></div>
            )}
          </div>
          <div className="pt-1">
            <h6 className={`mb-0 ${currentStep === index ? 'text-primary fw-bold' : ''}`}>{label}</h6>
          </div>
        </div>
      ))}
    </div>
  );
}

export default LoanStepper;