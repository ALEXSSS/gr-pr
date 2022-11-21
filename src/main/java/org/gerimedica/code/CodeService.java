package org.gerimedica.code;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

public interface CodeService {

    Code getCodeByCode(String code);

    List<Code> getAll();

    void saveCode(Code code);

    void saveCodes(List<Code> code);

    void saveFromCsvInputStream(InputStream in);

    void getToCsvOutputStream(OutputStream out);

    void deleteCode(String code);
}
