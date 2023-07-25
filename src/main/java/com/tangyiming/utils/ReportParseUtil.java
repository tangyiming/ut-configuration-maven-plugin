package com.tangyiming.utils;

import com.tangyiming.data.DiffCoverageData;
import com.tangyiming.data.Consts;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.apache.maven.project.MavenProject;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ReportParseUtil {
    /**
     * parse the data from target/site/diffCoverage/diff-coverage.csv to json
     *
     * @param project MavenProject
     */
    public static List<DiffCoverageData> parseDiffCoverageDate(MavenProject project) {
        List<DiffCoverageData> coverageDataList = new ArrayList<>();
        try {
            FileReader fileReader = new FileReader(project.getBasedir() + Consts.DIFF_COVERAGE_CSV_FILE_PATH);
            CSVFormat csvFormat = CSVFormat.DEFAULT.withFirstRecordAsHeader();
            CSVParser csvParser = new CSVParser(fileReader, csvFormat);
            for (CSVRecord csvRecord : csvParser) {
                String group = csvRecord.get("GROUP");
                String packageName = csvRecord.get("PACKAGE");
                String className = csvRecord.get("CLASS");
                int instructionMissed = Integer.parseInt(csvRecord.get("INSTRUCTION_MISSED"));
                int instructionCovered = Integer.parseInt(csvRecord.get("INSTRUCTION_COVERED"));
                int branchMissed = Integer.parseInt(csvRecord.get("BRANCH_MISSED"));
                int branchCovered = Integer.parseInt(csvRecord.get("BRANCH_COVERED"));
                int lineMissed = Integer.parseInt(csvRecord.get("LINE_MISSED"));
                int lineCovered = Integer.parseInt(csvRecord.get("LINE_COVERED"));
                int complexityMissed = Integer.parseInt(csvRecord.get("COMPLEXITY_MISSED"));
                int complexityCovered = Integer.parseInt(csvRecord.get("COMPLEXITY_COVERED"));
                int methodMissed = Integer.parseInt(csvRecord.get("METHOD_MISSED"));
                int methodCovered = Integer.parseInt(csvRecord.get("METHOD_COVERED"));

                DiffCoverageData coverageData = new DiffCoverageData(group, packageName, className, instructionMissed,
                        instructionCovered, branchMissed, branchCovered, lineMissed, lineCovered, complexityMissed,
                        complexityCovered, methodMissed, methodCovered);

                coverageDataList.add(coverageData);
            }
            csvParser.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return coverageDataList;
    }
}
