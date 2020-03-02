/*
    Name: James McCulloch, Logan Doughty, Tamara Wold, Nathan-Fittler-Willis
    Student No: 3291441, 3276255, 3088810 , 3189731
    Date: 08/06/2019
    Course: SENG2050
    Assignment: Assignment 3

    Description: An enumeration of the status the issue can have.
 */

package models;


public enum IssueStatus {
	KNOWLEDGE_BASE, WAITING_THIRDPARTY, WAITING_REPORTER, COMPLETED, RESOLVED, NEW, NOT_ACCEPTED, IN_PROGRESS;


	public static IssueStatus getIssueStatus(String status) {
		IssueStatus stat = null;

		if (status.equals("network")) {
			stat = IssueStatus.KNOWLEDGE_BASE;
		} else if (status.equals("waiting3P")) {
			stat = IssueStatus.WAITING_THIRDPARTY;
		} else if (status.equals("waitingReport")) {
			stat = IssueStatus.WAITING_REPORTER;
		}  else if (status.equals("completed")) {
			stat = IssueStatus.COMPLETED;
		}  else if (status.equals("resolved")) {
			stat = IssueStatus.RESOLVED;
		}  else if (status.equals("not_accepted")) {
			stat = IssueStatus.NOT_ACCEPTED;
		} else if (status.equals("in_progress")) {
			stat = IssueStatus.IN_PROGRESS;
		} else if (status.equals("new")) {
			stat = IssueStatus.NEW;
		}

		return stat;
	}
}
