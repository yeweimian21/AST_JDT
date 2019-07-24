package edu.ncsu.csc.itrust.enums;


/**
 * All of the possible transaction types, in no particular order, used in producing the operational profile.
 * TransactionCode -- identification code
 * Description -- basic description of the event
 * ActionPhrase -- a patient-centered English sentence describing the action (used in activity feeds)
 * PatientViewable -- boolean for if the action will be displayed in the patient activity feed
 */
public enum TransactionType {
	/**LOGIN_FAILURE*/
	LOGIN_FAILURE(1, "Failed login", "tried to authenticate unsuccessfully", true),
	/**HOME_VIEW*/
	HOME_VIEW(10, "User views homepage", "has viewed the iTrust user homepage", false),
	/**UNCAUGHT_EXCEPTION*/
	UNCAUGHT_EXCEPTION(20, "Exception occured and was not caught", "caused an unhandled exception", false),
	/**GLOBAL_PREFERENCES_VIEW*/
	GLOBAL_PREFERENCES_VIEW(30, "View global preferences", "viewed global preferences for iTrust", false),
	/**GLOBAL_PREFERENCES_EDIT*/
	GLOBAL_PREFERENCES_EDIT(31, "Edit global preferences", "edited global preferences for iTrust", false),
	/**USER_PREFERENCES_VIEW*/
	USER_PREFERENCES_VIEW(32, "View preferences", "viewed user preferences", false),
	/**USER_PREFERENCES_EDIT*/
	USER_PREFERENCES_EDIT(33, "Edit peferences", "edited user preferences", false),
	/**PATIENT_CREATE*/
	PATIENT_CREATE(100, "Create a patient", "created your account", true),
	/**PATIENT_DISABLE*/
	PATIENT_DISABLE(101, "Disable a patient", "disabled your account", true),
	/**PATIENT_DEACTIVATE*/
	PATIENT_DEACTIVATE(102, "Deactivate a patient", "deactivated your account", true),
	/**PATIENT_ACTIVATE*/
	PATIENT_ACTIVATE(103, "Activate a patient", "activated your account", true),
	/**LHCP_CREATE*/
	LHCP_CREATE(200, "Create a LHCP", "created an LHCP", false),
	/**LHCP_EDIT*/
	LHCP_EDIT(201, "Edit LHCP information", "edited LHCP information", false),
	/**LHCP_DISABLE*/
	LHCP_DISABLE(202, "Disable a LHCP", "disbaled an LHCP", false),
	/**ER_CREATE*/
	ER_CREATE(210, "Create an ER", "created an ER", false),
	/**ER_EDIT*/
	ER_EDIT(211, "Edit ER information", "edited ER information", false),
	/**ER_DISABLE*/
	ER_DISABLE(212, "Disable an ER", "disabled an ER", false),
	/**PHA_CREATE*/
	PHA_CREATE(220, "Create a PHA", "created a PHA", false),
	/**PHA_EDIT*/
	PHA_EDIT(221, "Edit PHA information", "edited PHA information", false),
	/**PHA_DISABLE*/
	PHA_DISABLE(222, "Disable a PHA", "disabled a PHA", false),
	/**LHCP_ASSIGN_HOSPITAL*/
	LHCP_ASSIGN_HOSPITAL(230, "Assign LHCP to a hospital", "assigned an LHCP to a hospital", false),
	/**LHCP_REMOVE_HOSPITAL*/
	LHCP_REMOVE_HOSPITAL(231, "Unassign LHCP from a hospital", "unassigned an LHCP from a hospital", false),
	/**LT_ASSIGN_HOSPITAL*/
	LT_ASSIGN_HOSPITAL(232, "Assign LT to a hospital", "assigned an LT to a hospital", false),
	/**LT_REMOVE_HOSPITAL*/
	LT_REMOVE_HOSPITAL(233, "Unassign LT from a hospital", "unassigned an LT from a hospital", false),
	/**UAP_CREATE*/
	UAP_CREATE(240, "Create a UAP", "created a UAP", false),
	/**UAP_EDIT*/
	UAP_EDIT(241, "Edit a UAP", "edited a UAP", false),
	/**UAP_DISABLE*/
	UAP_DISABLE(242, "Disable a UAP", "disabled a UAP", false),
	/**PERSONNEL_VIEW*/
	PERSONNEL_VIEW(250, "View a personnel's information", "viewed a personnel member's information", false),
	/**LT_CREATE*/
	LT_CREATE(260, "Create a LT", "created an LT", false),
	/**LT_EDIT*/
	LT_EDIT(261, "Edit LT information", "edited LT information", false),
	/**LT_DISABLE*/
	LT_DISABLE(262, "Disable a LT", "disabled an LT", false),
	/**LOGIN_SUCCESS*/
	LOGIN_SUCCESS(300, "Login Succeded", "successfully authenticated", true),
	/**LOGOUT*/
	LOGOUT(301, "Logged out", "logged out of iTrust", true),
	/**LOGOUT_INACTIVE*/
	LOGOUT_INACTIVE(302, "Logged out from inactivity", "logged out due to inactivity", true),
	/**PASSWORD_RESET*/
	PASSWORD_RESET(310, "Password changed", "changed your iTrust password", true),
	/**DEMOGRAPHICS_VIEW*/
	DEMOGRAPHICS_VIEW(400, "View patient demographics", "viewed your demographics", true),
	/**DEMOGRAPHICS_EDIT*/
	//Set to false as per the requirements page for UC43 as it is not supposed to be shown for LHCP or DLHCP
	DEMOGRAPHICS_EDIT(410, "Edit patient demographics", "edited your demographics", false),
	/**PATIENT_PHOTO_UPLOAD*/
	PATIENT_PHOTO_UPLOAD(411, "Upload patient photo", "uploaded a photo of you to your record", false),
	/**PATIENT_PHOTO_REMOVE*/
	PATIENT_PHOTO_REMOVE(412, "Remove patient photo", "removed your photograph from your record", false),
	/**DEPEND_DEMOGRAPHICS_EDIT*/
	DEPEND_DEMOGRAPHICS_EDIT(421, "Edit dependent demographics", "edited dependent's demographics", true),
	/**LHCP_VIEW*/
	LHCP_VIEW(600, "View LHCP information", "viewed LHCP information", false),
	/**LHCP_DECLARE_DLHCP*/
	LHCP_DECLARE_DLHCP(601, "Declare LHCP as DLHCP", "declared a LHCP", true),
	/**LHCP_UNDECLARE_DLHCP*/
	LHCP_UNDECLARE_DLHCP(602, "Undeclare LHCP as DLHCP", "undeclared an LHCP", true),
	/**ACCESS_LOG_VIEW*/
	ACCESS_LOG_VIEW(800, "View access log", "viewed your access log", false),
	/**MEDICAL_RECORD_VIEW*/
	MEDICAL_RECORD_VIEW(900, "View medical records", "viewed your medical records", true),
	/**PATIENT_HEALTH_INFORMATION_VIEW*/
	PATIENT_HEALTH_INFORMATION_VIEW(1000, "View personal health information", "viewed your basic health information", true),
	/**PATIENT_HEALTH_INFORMATION_EDIT*/
	PATIENT_HEALTH_INFORMATION_EDIT(1001, "Enter/edit personal health information", "edited your basic health information", true),
	/**BASIC_HEALTH_CHARTS_VIEW*/
	BASIC_HEALTH_CHARTS_VIEW(1002, "View Basic Health Charts", "viewed you basic health information charts", true),
	/**OFFICE_VISIT_CREATE*/
	OFFICE_VISIT_CREATE(1100, "Create Office Visits", "created an office visit", true),
	/**OFFICE_VISIT_VIEW*/
	OFFICE_VISIT_VIEW(1101, "View Office Visits", "viewed your office visit", true),
	/**OFFICE_VISIT_EDIT*/
	OFFICE_VISIT_EDIT(1102, "Edit Office Visits", "edited your office visit", true),
	/**PRESCRIPTION_ADD*/
	PRESCRIPTION_ADD(1110, "Add Prescription", "Added a prescription to your office visit", true),
	/**PRESCRIPTION_EDIT*/
	PRESCRIPTION_EDIT(1111, "Edit Prescription", "Edited a prescription in your office visit", true),
	/**PRESCRIPTION_REMOVE*/
	PRESCRIPTION_REMOVE(1112, "Remove Prescription", "Removed a prescription from your office visit", true),
	/**LAB_PROCEDURE_ADD*/
	LAB_PROCEDURE_ADD(1120, "Add Lab Procedure", "Added a lab procedure to your office visit", true),
	/**LAB_PROCEDURE_EDIT*/
	LAB_PROCEDURE_EDIT(1121, "Edit Lab Procedure", "Edited a lab procedure in your office visit", true),
	/**LAB_PROCEDURE_REMOVE*/
	LAB_PROCEDURE_REMOVE(1122, "Remove Lab Procedure", "Removed a lab procedure from your office visit", true),
	/**DIAGNOSIS_ADD*/
	DIAGNOSIS_ADD(1130, "Add Diagnosis", "Added a diagnosis to your office visit", true),
	/**DIAGNOSIS_REMOVE*/
	DIAGNOSIS_REMOVE(1132, "Remove Diagnosis", "Removed a diagnosis from your office visit", true),
	/**DIAGNOSIS_URL_EDIT*/
	DIAGNOSIS_URL_EDIT(1133, "Edit Diagnosis URL", "Diagnosis URL was edited", true),
	/**PROCEDURE_ADD*/
	PROCEDURE_ADD(1140, "Add Procedure", "Added a procedure to your office visit", true),
	/**PROCEDURE_EDIT*/
	PROCEDURE_EDIT(1141, "Edit Procedure", "Edited a procedure in your office visit", true),
	/**PROCEDURE_REMOVE*/
	PROCEDURE_REMOVE(1142, "Remove Procedure", "Removed a procedure from your office visit", true),
	/**IMMUNIZATION_ADD*/
	IMMUNIZATION_ADD(1150, "Add Immunization", "Added an immunization to your office visit", true),
	/**IMMUNIZATION_REMOVE*/
	IMMUNIZATION_REMOVE(1152, "Remove Immunization", "Removed an immunization from your office visit", true),
	/**OFFICE_VISIT_BILLED*/
	OFFICE_VISIT_BILLED(1160, "Office Visit Billed", "Billed an office visit", false),
	/**OPERATIONAL_PROFILE_VIEW*/
    OPERATIONAL_PROFILE_VIEW(1200, "View Operational Profile", "viewed the operational profile", false),
    /**HEALTH_REPRESENTATIVE_DECLARE*/
    HEALTH_REPRESENTATIVE_DECLARE(1300, "Declare personal health representative", "declared a personal health representative", true),
    /**HEALTH_REPRESENTATIVE_UNDECLARE*/
    HEALTH_REPRESENTATIVE_UNDECLARE(1301, "Undeclare personal health representative", "undeclared a personal health representative", true),
    /**MEDICAL_PROCEDURE_CODE_ADD*/
    MEDICAL_PROCEDURE_CODE_ADD(1500, "Add Medical procedure code", "added a medical procedure code", false),
    /**MEDICAL_PROCEDURE_CODE_VIEW*/
    MEDICAL_PROCEDURE_CODE_VIEW(1501, "View Medical procedure code", "viewed a medical procedure code", false),
    /**MEDICAL_PROCEDURE_CODE_EDIT*/
    MEDICAL_PROCEDURE_CODE_EDIT(1502, "Edit Medical procedure code", "edited a medical procedure code", false),
    /**IMMUNIZATION_CODE_ADD*/
    IMMUNIZATION_CODE_ADD(1510, "Add Immunization code", "added an immunization code", false),
    /**IMMUNIZATION_CODE_VIEW*/
    IMMUNIZATION_CODE_VIEW(1511, "View Immunization code", "viewed an immunization code", false),
    /**IMMUNIZATION_CODE_EDIT*/
    IMMUNIZATION_CODE_EDIT(1512, "Edit Immunization code", "edited an immunization code", false),
    /**DIAGNOSIS_CODE_ADD*/
    DIAGNOSIS_CODE_ADD(1520, "Add Diagnosis code", "added a diagnosis code", false),
    /**DIAGNOSIS_CODE_VIEW*/
    DIAGNOSIS_CODE_VIEW(1521, "View Diagnosis code", "viewed a diagnosis code", false),
    /**DIAGNOSIS_CODE_EDIT*/
    DIAGNOSIS_CODE_EDIT(1522, "Edit Diagnosis code", "edited a diagnosis code", false),
    /**DRUG_CODE_ADD*/
    DRUG_CODE_ADD(1530, "Add Drug code", "added a drug code", false),
    /**DRUG_CODE_VIEW*/
    DRUG_CODE_VIEW(1531, "View Drug code", "viewed a drug code", false),
    /**DRUG_CODE_EDIT*/
    DRUG_CODE_EDIT(1532, "Edit Drug code", "edited a drug code", false),
    /**DRUG_CODE_REMOVE*/
    DRUG_CODE_REMOVE(1533, "Remove Drug code", "removed a drug code", false),
    /**LOINC_CODE_ADD*/
    LOINC_CODE_ADD(1540, "Add Physical Services code", "added a physical service code", false),
    /**LOINC_CODE_VIEW*/
    LOINC_CODE_VIEW(1541, "View Physical Services code", "viewed a physical service code", false),
    /**LOINC_CODE_EDIT*/
    LOINC_CODE_EDIT(1542, "Edit Physical Services code", "edited a physical service code", false),
    /**LOINC_CODE_FILE_ADD*/
    LOINC_CODE_FILE_ADD(1549, "Upload Physical Services file", "uploaded a LOINC file", false),
    /**RISK_FACTOR_VIEW*/
    RISK_FACTOR_VIEW(1600, "View risk factors", "viewed your risk factors", true),
    /**PATIENT_REMINDERS_VIEW*/
    PATIENT_REMINDERS_VIEW(1700, "Proactively determine necessary patient care", "proactively determined your necessary patient care", true),
    /**HOSPITAL_LISTING_ADD*/
    HOSPITAL_LISTING_ADD(1800, "Add a hospital listing", "added a hospital listing", false),
    /**HOSPITAL_LISTING_VIEW*/
    HOSPITAL_LISTING_VIEW(1801, "View a hospital listing", "viewed a hospital listing", false),
    /**HOSPITAL_LISTING_EDIT*/
    HOSPITAL_LISTING_EDIT(1802, "Edit a hospital listing", "edited a hospital listing", false),
    /**PRESCRIPTION_REPORT_VIEW*/
    PRESCRIPTION_REPORT_VIEW(1900, "View prescription report", "viewed your prescription report", true),
    /**DEATH_TRENDS_VIEW*/
    DEATH_TRENDS_VIEW(2000, "View Cause of Death Trends", "viewed cause of death trends", false),
    /**EMERGENCY_REPORT_CREATE*/
    EMERGENCY_REPORT_CREATE(2100, "Create emergency report", "created an emergency report for you", true),
    /**EMERGENCY_REPORT_VIEW*/
    EMERGENCY_REPORT_VIEW(2101, "View emergency report", "viewed your emergency report", true),
    /**APPOINTMENT_TYPE_ADD*/
    APPOINTMENT_TYPE_ADD(2200, "Add appointment type listing", "added an appointment type", false),
    /**APPOINTMENT_TYPE_VIEW*/
    APPOINTMENT_TYPE_VIEW(2201, "View appointment type listing", "viewed an appointment type", false),
    /**APPOINTMENT_TYPE_EDIT*/
    APPOINTMENT_TYPE_EDIT(2202, "Edit appointment type listing", "edited an appointment type", false),
    /**APPOINTMENT_ADD*/
    APPOINTMENT_ADD(2210, "Schedule Appointments", "scheduled an appointment with you", true),
    /**APPOINTMENT_VIEW*/
    APPOINTMENT_VIEW(2211, "View Scheduled Appointment", "viewed your scheduled appointment", true),
    /**APPOINTMENT_EDIT*/
    APPOINTMENT_EDIT(2212, "Edit Scheduled Appointment", "edited your scheduled appointment", true),
    /**APPOINTMENT_REMOVE*/
    APPOINTMENT_REMOVE(2213, "Delete Scheduled Appointment", "canceled your scheduled appointment", true),
    /**APPOINTMENT_ALL_VIEW*/
    APPOINTMENT_ALL_VIEW(2220, "View All Scheduled Appointments", "viewed all scheduled appointments", true),
    /**APPOINTMENT_CONFLICT_OVERRIDE*/
    APPOINTMENT_CONFLICT_OVERRIDE(2230, "Schedule conflict override", "overrided a schedule conflict", true),
    /**APPOINTMENT_REQUEST_SUBMITTED*/
    APPOINTMENT_REQUEST_SUBMITTED(2240, "Appointment request submitted", "requested an appointment", true),
    /**APPOINTMENT_REQUEST_APPROVED*/
    APPOINTMENT_REQUEST_APPROVED(2250, "Appointment request approved", "approved an appointment request", true),
    /**APPOINTMENT_REQUEST_REJECTED*/
    APPOINTMENT_REQUEST_REJECTED(2251, "Appointment request rejected", "rejected an appointment request", true),
    /**APPOINTMENT_REQUEST_VIEW*/
    APPOINTMENT_REQUEST_VIEW(2260, "View All Appointment Requests", "viewed all appointment requests", true),
    /**COMPREHENSIVE_REPORT_VIEW*/
    COMPREHENSIVE_REPORT_VIEW(2300, "Comprehensive Report", "viewed your comprehensive report", true),
    /**COMPREHENSIVE_REPORT_ADD*/
    COMPREHENSIVE_REPORT_ADD(2301, "Request Comprehensive Report", "requested a comprehensive report for you", true),
    /**SATISFACTION_SURVEY_TAKE*/
    SATISFACTION_SURVEY_TAKE(2400, "Take Satisfaction Survey", "completed a satisfaction survey", true),
    /**SATISFACTION_SURVEY_VIEW*/
    SATISFACTION_SURVEY_VIEW(2500, "View physician satisfaction results", "viewed physician satisfaction survey results", false),
    /**LAB_RESULTS_UNASSIGNED*/
    LAB_RESULTS_UNASSIGNED(2600, "Unassigned lab results log value", "", false),
    /**LAB_RESULTS_CREATE*/
    LAB_RESULTS_CREATE(2601, "Create laboratory procedure", "created your lab procedure", true),
    /**LAB_RESULTS_VIEW*/
    LAB_RESULTS_VIEW(2602, "View laboratory procedure results", "viewed your lab procedure results", true),
    /**LAB_RESULTS_REASSIGN*/
    LAB_RESULTS_REASSIGN(2603, "Reassign laboratory test to a new lab technician", "assigned your laboratory test to a new lab tech", false),
    /**LAB_RESULTS_REMOVE*/
    LAB_RESULTS_REMOVE(2604, "Remove a laboratory procedure", "removed your lab procedure", true),
    /**LAB_RESULTS_ADD_COMMENTARY*/
    LAB_RESULTS_ADD_COMMENTARY(2605, "Add commentary to a laboratory procedure", "marked a lab procedure as completed", true),
    /**LAB_RESULTS_VIEW_QUEUE*/
    LAB_RESULTS_VIEW_QUEUE(2606, "View laboratory procedure queue", "viewed your lab procedure queue", false),
    /**LAB_RESULTS_RECORD*/
    LAB_RESULTS_RECORD(2607, "Record laboratory test results", "recorded your lab procedure results", false),
    /**LAB_RESULTS_RECEIVED*/
    LAB_RESULTS_RECEIVED(2608, "Laboratory procedure received by lab technician", "the lab tech received your lab procedure", false),
    /**EMAIL_SEND*/
    EMAIL_SEND(2700, "Send an email", "sent an email", false),
    /**EMAIL_HISTORY_VIEW*/
    EMAIL_HISTORY_VIEW(2710, "View email history", "viewed email history", false),
    /**PATIENT_LIST_VIEW*/
    PATIENT_LIST_VIEW(2800, "View patient list", "viewed his/her patient list", false),
    /**EXPERIENCED_LHCP_FIND*/
    EXPERIENCED_LHCP_FIND(2900, "Find LHCPs with experience with a diagnosis", "found LHCPs with experience with a diagnosis", false),
    /**MESSAGE_SEND*/
    MESSAGE_SEND(3000, "Send messages", "sent a message", false),
    /**MESSAGE_VIEW*/
    MESSAGE_VIEW(3001, "View messages", "viewed a message", false),
    /**INBOX_VIEW*/
    INBOX_VIEW(3010, "View inbox", "viewed message inbox", false),
    /**OUTBOX_VIEW*/
    OUTBOX_VIEW(3011, "View outbox", "viewed message outbox", false),
    /**PATIENT_FIND_LHCP_FOR_RENEWAL*/
    PATIENT_FIND_LHCP_FOR_RENEWAL(3100, "Find LHCPs for prescription renewal", "found LHCPs for prescription renewal", true),
    /**EXPIRED_PRESCRIPTION_VIEW*/
    EXPIRED_PRESCRIPTION_VIEW(3110, "View expired prescriptions", "viewed your expired prescriptions", true),
    /**PRECONFIRM_PRESCRIPTION_RENEWAL*/
    PRECONFIRM_PRESCRIPTION_RENEWAL(3200, "Proactively confirm prescription-renewal needs", "proactively confirmed your prescription renewal needs", true),
    /**DIAGNOSES_LIST_VIEW*/
    DIAGNOSES_LIST_VIEW(3210, "View list of diagnoses", "viewed a list of diagnosis", false),
    /**CONSULTATION_REFERRAL_CREATE*/
    CONSULTATION_REFERRAL_CREATE(3300, "Refer a patient for consultations", "referred you to another HCP", true),
    /**CONSULTATION_REFERRAL_VIEW*/
    CONSULTATION_REFERRAL_VIEW(3310, "View referral for consultation", "viewed your consultation referral", true),
    /**CONSULTATION_REFERRAL_EDIT*/
    CONSULTATION_REFERRAL_EDIT(3311, "Modify referral for consultation", "modified your consultation referral", true),
    /**CONSULTATION_REFERRAL_CANCEL*/
    CONSULTATION_REFERRAL_CANCEL(3312, "Cancel referral for consultation", "canceled your consultation referral", true),
    /**PATIENT_LIST_ADD*/
    PATIENT_LIST_ADD(3400, "Patient added to monitoring list", "added you to the telemedicine monitoring list", true),
    /**PATIENT_LIST_EDIT*/
    PATIENT_LIST_EDIT(3401, "Patient telemedicine permissions changed", "changed the data you report for telemedicine monitoring", true),
    /**PATIENT_LIST_REMOVE*/
    PATIENT_LIST_REMOVE(3402, "Patient removed from monitoring list", "removed you from the telemedicine monitoring list", true),
    /**TELEMEDICINE_DATA_REPORT*/
    TELEMEDICINE_DATA_REPORT(3410, "Remote monitoring levels are reported", "reported your telemedicine data", true),
    /**TELEMEDICINE_DATA_VIEW*/
    TELEMEDICINE_DATA_VIEW(3420, "Remote monitoring levels are viewed", "viewed your telemedicine data reports", true),
    /**ADVERSE_EVENT_REPORT*/
    ADVERSE_EVENT_REPORT(3500, "Adverse event reporting", "reported an adverse event", true),
    /**ADVERSE_EVENT_VIEW*/
    ADVERSE_EVENT_VIEW(3600, "Adverse event monitoring", "reviewed an adverse event report", false),
    /**ADVERSE_EVENT_REMOVE*/
    ADVERSE_EVENT_REMOVE(3601, "Remove adverse event", "removed an adverse event report", false),
    /**ADVERSE_EVENT_REQUEST_MORE*/
    ADVERSE_EVENT_REQUEST_MORE(3602, "Request More Adverse Event Details", "requested more adverse event details", false),
    /**ADVERSE_EVENT_CHART_VIEW*/
    ADVERSE_EVENT_CHART_VIEW(3603, "View Adverse Event Chart", "viewed adverse event chart", false),
    /**OVERRIDE_INTERACTION_WARNING*/
    OVERRIDE_INTERACTION_WARNING(3700, "Override interaction warning", "Overrode an interaction warning", true),
    /**OVERRIDE_CODE_ADD*/
    OVERRIDE_CODE_ADD(3710, "Add overriding reason listing", "added a medication override reason", false),
    /**OVERRIDE_CODE_EDIT*/
    OVERRIDE_CODE_EDIT(3711, "Edit overriding reason listing", "edited a medication override reason", false),
    /**DRUG_INTERACTION_ADD*/
    DRUG_INTERACTION_ADD(3800, "Add Drug Interactions Code", "added a drug interaction code", false),
    /**DRUG_INTERACTION_EDIT*/
    DRUG_INTERACTION_EDIT(3801, "Edit Drug Interactions Code", "edited a drug interaction code", false),
    /**DRUG_INTERACTION_DELETE*/
    DRUG_INTERACTION_DELETE(3802, "Delete Drug Interactions Code", "deleted a drug interaction code", false),
    /**CALENDAR_VIEW*/
    CALENDAR_VIEW(4000, "View calendar", "viewed your calendar", false),
    /**UPCOMING_APPOINTMENTS_VIEW*/
    UPCOMING_APPOINTMENTS_VIEW(4010, "View Upcoming Appointments", "viewed upcoming appointments", false),
    /**NOTIFICATIONS_VIEW*/
    NOTIFICATIONS_VIEW(4200, "View Notifications", "viewed your notification center", false),
    /**ACTIVITY_FEED_VIEW*/
    ACTIVITY_FEED_VIEW(4300, "View activity feed", "viewed your activity feed", false),
    /**PATIENT_INSTRUCTIONS_ADD*/
    PATIENT_INSTRUCTIONS_ADD(4400, "Add Patient Specific Instructions for Office Visit", "added a patient-specific instruction", true),
    /**PATIENT_INSTRUCTIONS_EDIT*/
    PATIENT_INSTRUCTIONS_EDIT(4401, "Edit Patient Specific Instructions for Office Visit", "edited a patient-specific instruction", true),
    /**PATIENT_INSTRUCTIONS_DELETE*/
    PATIENT_INSTRUCTIONS_DELETE(4402, "	Delete Patient Specific Instructions for Office Visit", "deleted a patient-specific instruction", true),
    /**PATIENT_INSTRUCTIONS_VIEW*/
    PATIENT_INSTRUCTIONS_VIEW(4403, "	View Patient Specific Instructions", "viewed your patient-specific instructions", true),
    /**DIAGNOSIS_TRENDS_VIEW*/
	DIAGNOSIS_TRENDS_VIEW(4500, "View diagnosis statistics", "viewed your diagnosis count", false),
	/**DIAGNOSIS_EPIDEMICS_VIEW*/
	DIAGNOSIS_EPIDEMICS_VIEW(4600, "View Epidemic Detection", "viewed epidemic detection", false),
	/**GROUP_REPORT_VIEW*/
	GROUP_REPORT_VIEW(4601, "View Group Report", "viewed group report", false),
	/**FIND_EXPERT*/
	FIND_EXPERT(4700, "Find an Expert", "searched for experts", true),
	/**FIND_EXPERT_ZIP_ERROR*/
	FIND_EXPERT_ZIP_ERROR(4701, "Find an Expert - Zip Code Error", "entered an incorrect ZIP code for Find an Expert", true),
	/**PATIENT_ASSIGNED_TO_ROOM*/
	PATIENT_ASSIGNED_TO_ROOM(4800, "Patient Assigned", "Patient assigned to room", false),
	/**PATIENT_REMOVED_FROM_ROOM*/
	PATIENT_REMOVED_FROM_ROOM(4801, "Patient Removed", "Patient removed from room", false),
	/**ROOMS_FULL*/
	ROOMS_FULL(4802, "Rooms Full", "Patients cannot be assigned to any room meeting that criteria", false),
	/**CREATE_BASIC_HEALTH_METRICS*/
	CREATE_BASIC_HEALTH_METRICS(5100, "Create Basic Health Metrics", "created a basic health metrics record for you", true),
	/**VIEW_BASIC_HEALTH_METRICS*/
	VIEW_BASIC_HEALTH_METRICS(5101, "View Basic Health Metrics", "viewed your basic health metrics", true),
	/**EDIT_BASIC_HEALTH_METRICS*/
	EDIT_BASIC_HEALTH_METRICS(5102, "Edit Basic Health Metrics", "editted your basic health metrics", true),
	/**REMOVE_BASIC_HEALTH_METRICS*/
	REMOVE_BASIC_HEALTH_METRICS(5103, "Remove Basic Health Metrics", "removed a basic health metrics record from your office visit", true),
	/**PATIENT_VIEW_BASIC_HEALTH_METRICS*/
	PATIENT_VIEW_BASIC_HEALTH_METRICS(5200, "Patient View of Basic Health Metrics", "viewed your health records history", true),
	/**HCP_VIEW_BASIC_HEALTH_METRICSc*/
	HCP_VIEW_BASIC_HEALTH_METRICS(5201, "HCP View of Basic Health Metrics", "viewed your health records history", true),	
	/**HCP_VIEW_PERCENTILES_CHART*/
	HCP_VIEW_PERCENTILES_CHART(5301, "HCP View of Basic Health Metric Percentiles", "viewed your percentile data.", true),
    /**PATIENT_VIEW_PERCENTILES_CHART*/
    PATIENT_VIEW_PERCENTILES_CHART(5300, "Pateint View of Basic Health Metric Percentiles", "viewed your percentile data.", true),
    /**ADMIN_UPLOAD_CDCMETRICS*/
    ADMIN_UPLOAD_CDCMETRICS(5302, "Admin upload of CDC Metrics", "Uploaded CDC Metrics Successfully", false),
    /**PASSWORD_CHANGE*/
	PASSWORD_CHANGE(5700, "User Successful Password Change", " changed password", false),
	/**PASSWORD_CHANGE_FAILED*/
	PASSWORD_CHANGE_FAILED(5701, "User Failed Password Change", " failed to change password", false),
	/**IMMUNIZATION_REPORT_PATIENT_VIEW*/
	IMMUNIZATION_REPORT_PATIENT_VIEW(5500, "Patient View of Immunization Report", " viewed their immunization report", true), 
	/**IMMUNIZATION_REPORT_HCP_VIEW*/
	IMMUNIZATION_REPORT_HCP_VIEW(5501, "HCP View of Immunization Report", " viewed patient's immunization report", true),
	/**PATIENT_RELEASE_HEALTH_RECORDS*/
	PATIENT_RELEASE_HEALTH_RECORDS(5600, "Patient Release of Health Records", "requested the release of your health records", true),
	/**PATIENT_VIEW_RELEASE_REQUEST*/
	PATIENT_VIEW_RELEASE_REQUEST(5601, "Patient View of Health Records Release", "viewed a records release request", true),
	/**HCP_RELEASE_APPROVAL*/
	HCP_RELEASE_APPROVAL(5602, "HCP Approval of Health Records Release", "approved your health records release request", true),
	/**HCP_RELEASE_DENIAL*/
	HCP_RELEASE_DENIAL(5603, "HCP Denial of Health Records Release", "denied your health records release request", true),
	/**UAP_RELEASE_APPROVAL*/
	UAP_RELEASE_APPROVAL(5604, "UAP Approval of Health Records Release", "approved your health records release request", true),
	/**UAP_RELEASE_DENIAL*/
	UAP_RELEASE_DENIAL(5605, "UAP Denial of Health Records Release", "denied your health records release request", true),
	/**HCP_CREATED_DEPENDENT_PATIENT*/
	HCP_CREATED_DEPENDENT_PATIENT(5800, "HCP Create Dependent Patient", "created your account as a dependent user", true),
	/**HCP_CHANGE_PATIENT_DEPENDENCY*/
	HCP_CHANGE_PATIENT_DEPENDENCY(5801, "HCP Change Patient Dependency Status", "changed your dependency status", true),
	/**PATIENT_VIEW_DEPENDENT_REQUESTS*/
	PATIENT_VIEW_DEPENDENT_REQUESTS(5900, "Patient View of Dependent's Release Requests", "viewed a dependent's records release request", true),
	/**PATIENT_REQUEST_DEPEDENT_RECORDS*/
	PATIENT_REQUEST_DEPEDENT_RECORDS(5901, "Patient Requests Dependent's Records Release", "requested a dependent's records release", true),
	/**PATIENT_VIEWS_BILLS*/
	PATIENT_BILLS_VIEW_ALL(6000, "Patient has viewed their list of bills", "viewed list of bills", true),
	/**PATIENT_VIEWS_BILL*/
	PATIENT_VIEWS_BILL(6001, "Patient has view a single bill", "viewed single bill", true),
	/**PATIENT_PAYS_BILL*/
	PATIENT_PAYS_BILL(6002, "Patient has paid a bill", "paid a bill", true),
	/**PATIENT_SUBMITS_INSURANCE*/
	PATIENT_SUBMITS_INSURANCE(6003, "Patient has sumbitted an insurance claim", "submitted insurance claim", true),
	/**UAP_INITIAL_APPROVAL*/
	UAP_INITIAL_APPROVAL(6004, "UAP approves the patient's first insurance claim", "approved initial claim", true),
	/**UAP_INITIAL_DENIAL*/
	UAP_INITIAL_DENIAL(6005, "UAP denies the patient's first insurance claim", "denied initial claim", true),
	/**UAP_SECOND_APPORVAL*/
	UAP_SECOND_APPROVAL(6006, "UAP approves the patient's second insurance claim", "approved second claim", true),
	/**UAP_SECOND_DENIAL*/
	UAP_SECOND_DENIAL(6007, "UAP denies the patient's second insurance claim", "denied second claim", true),
	/**PATIENT_RESUBITS_INSURANCE*/
	PATIENT_RESUBMITS_INSURANCE(6008, "Patient submits a second insurance claim", "submitted second insurnace claim", true),
	/**VIEW_EXPERT_SEARCH*/
	VIEW_EXPERT_SEARCH_NAME(6100, "Pateint viewed the search expert by name page.", "searched for an expert by name", false),
	/**VIEW_REVIEWS*/
	VIEW_REVIEWS(6101, "Patient viewed reviews for an HCP.", "viewed reviews", false),
	/**SUBMIT_REVIEW*/
	SUBMIT_REVIEW(6102, "Patient submitted a review for an HCP.", "submitted review", true),
	/**CREATE_INITIAL_OBSTETRICS_RECORD**/
	CREATE_INITIAL_OBSTETRICS_RECORD(6300, "Create Initial Obstetric Record", "Create Initial Obstetric Record", true),
	/**VIEW_INITIAL_OBSTETRICS_RECORD**/
	VIEW_INITIAL_OBSTETRICS_RECORD(6301, "View Initial Obstetric Record", "View Initial Obstetric Record", true),
	/**CREATE_INITIAL_OBSTETRICS_RECORD**/
	CREATE_OBSTETRICS_OV(6400, "Create Obstetric Office Visit", "Create Obstetric Office Visit", true),
	/**VIEW_INITIAL_OBSTETRICS_RECORD**/
	VIEW_OBSTETRICS_OV(6401, "View Obstetric Office Visit", "View Obstetric Office Visit", true),
	/**VIEW_INITIAL_OBSTETRICS_RECORD**/
	EDIT_OBSTETRICS_OV(6402, "Edit Obstetric Office Visit", "Edit Obstetric Office Visit", true),
	/**ADD_ALLERGY**/
	ADD_ALLERGY(6700, "Add Allergy", "Add Allergy", true),
	
	/**Added to the Food Diary **/
	FOOD_DIARY_ADD(6800, "Add Food Entry", "added an entry to your Food Diary", true),
	/**Patient viewed food diary**/
	PATIENT_VIEW_FOOD_DIARY(6801, "Patient Viewed Food Diary", "viewed your Food Diary", true),
	/**Nutritionist viewed food diary **/
	NUTRITIONIST_VIEW_FOOD_DIARY(6900, "Nutritionist viewed Food Diary", "viewed your Food Diary", true),
	/** Patient edited an entry from their food diary */
	PATIENT_EDIT_FOOD_ENTRY(7000, "Patient edited Food Diary entry", "edited an entry in your Food Diary", true),
	/** Patient deleted an entry from their food diary */
	DELETE_FOOD_ENTRY(7001, "Patient deleted Food Diary entry", "deleted an entry from your Food Diary", true),
		
	/** Patient changed their designated Nutritionist */
	EDIT_DESIGNATED_NUTRITIONIST(7300, "Patient edited designated Nutritionist", "edited your designated Nutritionist", true),
	
	/**Added to the Sleep Diary **/
	SLEEP_DIARY_ADD(7700, "Add Sleep Entry", "added an entry to your Sleep Diary", true),
	/**Patient viewed Sleep diary**/
	PATIENT_VIEW_SLEEP_DIARY(7801, "Patient Viewed Sleep Diary", "viewed your Sleep Diary", true),
	/**HCP viewed Sleep diary **/
	HCP_VIEW_SLEEP_DIARY(7802, "HCP viewed Sleep Diary", "viewed your Sleep Diary", true),
	/** Patient edited an entry from their Sleep diary */
	PATIENT_EDIT_SLEEP_ENTRY(7901, "Patient edited Sleep Diary entry", "edited an entry in your Sleep Diary", true),
	/** Patient deleted an entry from their Sleep diary */
	DELETE_SLEEP_ENTRY(7902, "Patient deleted Sleep Diary entry", "deleted an entry from your Sleep Diary", true),
	
	/**Added to the Exercise Diary **/
	EXERCISE_DIARY_ADD(8000, "Add Exercise Entry", "added an entry to your Exercise Diary", true),
	/**Patient viewed Exercise diary**/
	PATIENT_VIEW_EXERCISE_DIARY(8101, "Patient Viewed Exercise Diary", "viewed your Exercise Diary", true),
	/**Trainer viewed Exercise diary **/
	TRAINER_VIEW_EXERCISE_DIARY(8102, "Trainer viewed Exercise Diary", "viewed your Exercise Diary", true),
	/** Patient edited an entry from their Exercise diary */
	PATIENT_EDIT_EXERCISE_ENTRY(8201, "Patient edited Exercise Diary entry", "edited an entry in your Exercise Diary", true),
	/** Patient deleted an entry from their Exercise diary */
	DELETE_EXERCISE_ENTRY(8202, "Patient deleted Exercise Diary entry", "deleted an entry from your Exercise Diary", true),
	
	/**Added to Labels **/
	LABEL_ADD(7400, "Add Label", "added a new Label", true),
	/**Patient edited a Label**/
	EDIT_LABEL(7401, "Patient edited a Label", "edited a Label", true),
	/**Patient deleted a Label **/
	DELETE_LABEL(7402, "Patient deleted a Label", "deleted a Label", true),
	/**Patient edited a Label**/
	HCP_VIEW_MACRONUTRIENTS(7200, "HCP viewed macronutrients", "viewed macronutrients", true),
	/**Patient edited a Label**/
	VIEW_MACRONUTRIENTS_GRAPH(7100, "Patient generated graph", "generated a graph", true),
	/**Patient edited a Label**/
	CALCULATE_MACRONUTRIENTS(7101, "Patient calculated macronutrients", "calculated macronutrients", true),
	
	/**CREATE_OPHTHALMOLOGY_OV**/
	CREATE_OPHTHALMOLOGY_OV(8300, "Create Ophthalmology Office Visit", "Ophthalmology Office Visit", true),
	/**VIEW_OPHTHALMOLOGY_OV**/
	VIEW_OPHTHALMOLOGY_OV(8301, "View Ophthalmology Office Visit", "View Ophthalmology Office Visit", true),
	/**EDIT_OPHTHALMOLOGY_OV**/
	EDIT_OPHTHALMOLOGY_OV(8302, "Edit Ophthalmology Office Visit", "Edit Ophthalmology Office Visit", true),
	/**PATIENT_VIEW_OPHTHALMOLOGY_OV**/
	PATIENT_VIEW_OPHTHALMOLOGY_OV(8400, "View Patient Ophthalmology Office Visit", "View Patient Ophthalmology Office Visit", true),
	/**PATIENT_VIEW_DEPENDENT_OPHTHALMOLOGY_OV**/
	PATIENT_VIEW_DEPENDENT_OPHTHALMOLOGY_OV(8401, "View Dependent Ophthalmology Office Visit", "View Dependent Ophthalmology Office Visit", true),
	
	/**CREATE_OPHTHALMOLOGY_SURGERY**/
	CREATE_OPHTHALMOLOGY_SURGERY(8600, "Create Ophthalmology Surgery", "Ophthalmology Surgery", true),
	/**VIEW_OPHTHALMOLOGY_SURGERY**/
	VIEW_OPHTHALMOLOGY_SURGERY(8601, "View Ophthalmology Surgery", "View Ophthalmology Surgery", true),
	/**EDIT_OPHTHALMOLOGY_SURGERY**/
	EDIT_OPHTHALMOLOGY_SURGERY(8602, "Edit Ophthalmology Surgery", "Edit Ophthalmology Surgery", true),
	/**PATIENT_VIEW_OPHTHALMOLOGY_SURGERY**/
	;
	/**
	 * This string is used in the SQL statement associated with pulling events for
	 * display in a patient's Access Log
	 */
	public static String patientViewableStr;
	
	static {
		for(TransactionType t : TransactionType.values())
		{
			if(t.isPatientViewable())
			{
				patientViewableStr += "," + t.code;
			}
		}
	}
	/**
	 * This string is used in the SQL statement associated with excluding events as specified in UC43
	 */
	
	
	public static final String dlhcpHiddenStr = "400, 900, 1000, 1001, 1100, 1101, 1102, 1110, 1111, 1112, 1120, 1121, 1122, 1130, 1131, 1132, 1140, 1141, 1142, 1150, 1152, 1600, 1700, 1900, 2101, 2300, 2601, 2602, 3200,5201";
	
	private TransactionType(int code, String description, String actionPhrase, boolean patientViewable) {
		this.code = code;
		this.description = description;
		this.actionPhrase = actionPhrase;
		this.patientView = patientViewable;
	}

	private int code;
	private String description;
	private String actionPhrase;
	private boolean patientView;

	/**
	 * getCode
	 * @return code
	 */
	public int getCode() {
		return code;
	}

	/**
	 * getDescription
	 * @return description
	 */
	public String getDescription() {
		return description;
	}
	
	/**
	 * getActionPhrase
	 * @return actionPhrase
	 */
	public String getActionPhrase() {
		return actionPhrase;
	}
	
	/**
	 * isPatientViewable
	 * @return patientView
	 */
	public boolean isPatientViewable(){
		return patientView;
	}
	
	/**
	 * parse
	 * @param code code
	 * @return type
	 */
	public static TransactionType parse(int code) {
		for (TransactionType type : TransactionType.values()) {
			if (type.code == code)
				return type;
		}
		throw new IllegalArgumentException("No transaction type exists for code " + code);
	}
	
}
