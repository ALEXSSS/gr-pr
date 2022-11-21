package org.gerimedica.code;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class CodeCsvServiceImpl implements CodeCsvService {

    private static final String[] HEADERS = new String[]{"source", "codeListCode", "code", "displayValue", "longDescription", "fromDate", "toDate", "sortingPriority"};

    @Override
    public List<Code> getAllCodes(InputStream in) {
        List<Code> codes = new ArrayList<>();
        boolean headerSkipped = false;
        try (CSVReader csvReader = new CSVReader(new InputStreamReader(in))) {
            String[] values;
            while ((values = csvReader.readNext()) != null) {
                if (!headerSkipped) {
                    headerSkipped = true;
                    continue;
                }
                codes.add(arrayToCode(values));
            }
        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Sent file is corrupted");
        }
        return codes;
    }

    @Override
    public void writeCodes(OutputStream out, List<Code> codes) {
        try (CSVWriter csvWriter = new CSVWriter(new OutputStreamWriter(out))) {
            csvWriter.writeNext(HEADERS);
            for (Code code : codes) {
                csvWriter.writeNext(codeToArray(code));
            }
        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private String[] codeToArray(Code code) {
        String[] result = new String[HEADERS.length];
        Arrays.fill(result, "");
        result[0] = code.getSource();
        result[1] = code.getCodeListCode();
        result[2] = code.getCode();
        result[3] = code.getDisplayValue();
        result[4] = code.getLongDescription();
        result[5] = code.getFromDate().format(localDateFormatter);
        LocalDate toDate = code.getToDate();
        if (toDate != null) {
            result[6] = code.getToDate().format(localDateFormatter);
        }
        Integer sortingPriority = code.getSortingPriority();
        if (sortingPriority != null) {
            result[7] = sortingPriority.toString();
        }
        return result;
    }

    private static Code arrayToCode(String[] arr) {
        Code code = new Code();
        code.setSource(arr[0]);
        code.setCodeListCode(arr[1]);
        code.setCode(arr[2]);
        code.setDisplayValue(arr[3]);
        if (StringUtils.isNotEmpty(arr[4])) {
            code.setLongDescription(arr[4]);
        }
        code.setFromDate(LocalDate.parse(arr[5], localDateFormatter));
        if (StringUtils.isNotEmpty(arr[6])) {
            code.setToDate(LocalDate.parse(arr[6], localDateFormatter));
        }
        if (StringUtils.isNotEmpty(arr[7])) {
            code.setSortingPriority(Integer.parseInt(arr[7]));
        }
        return code;
    }

    private static final DateTimeFormatter localDateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
}
