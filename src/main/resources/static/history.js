async function loadHistory() {

    let response = await fetch(
        "http://localhost:8080/api/resume/all"
    );

    let data = await response.json();

    let tableBody =
        document.getElementById("historyBody");

    tableBody.innerHTML = "";

    data.reverse().forEach(resume => {

        let scoreClass = "";

        if (resume.score >= 80) {
            scoreClass = "score-high";
        }
        else if (resume.score >= 50) {
            scoreClass = "score-medium";
        }
        else {
            scoreClass = "score-low";
        }

        tableBody.innerHTML += `
        <tr>

            <td>
                <input
                    type="checkbox"
                    class="resumeCheck"
                    value="${resume.id}">
            </td>

            <td>${resume.id}</td>

            <td>${resume.fileName ?? "-"}</td>

            <td>
                <div class="score-circle ${scoreClass}">
                    ${resume.score}
                </div>
            </td>

            <td>${resume.matchedSkills}</td>

            <td>${resume.missingSkills ?? "-"}</td>

            <td>
                <span class="suggestion-badge">
                    ${resume.suggestions}
                </span>
            </td>

            <td>${resume.uploadedAt ?? "-"}</td>

        </tr>
        `;
    });
}

async function deleteSelected() {

    let selected =
        document.querySelectorAll(
            ".resumeCheck:checked"
        );

    if (selected.length === 0) {
        alert("Select records first.");
        return;
    }

    if (!confirm("Delete selected records ?")) {
        return;
    }

    for (let item of selected) {

        await fetch(
            `http://localhost:8080/api/resume/delete/${item.value}`,
            {
                method: "DELETE"
            }
        );
    }

    await loadHistory();

    alert("Records Deleted Successfully");
}

window.onload = loadHistory;