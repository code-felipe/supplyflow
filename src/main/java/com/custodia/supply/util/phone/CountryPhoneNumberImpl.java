package com.custodia.supply.util.phone;

import java.io.InputStream;
import java.util.List;


import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;


import com.custodia.supply.util.country.CountryPhone;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class CountryPhoneNumberImpl implements ICountryPhone{
	
	private final ObjectMapper mapper = new ObjectMapper();
	 /*
	  * {
    "name": "Åland Islands",
    "flag": "🇦🇽",
    "code": "AX",
    "dial_code": "+358"
  },
	  */
	@Override
	public List<CountryPhone> loadContries() {
		try (InputStream is = new ClassPathResource("static/data/countries.json").getInputStream()) {
            // Leemos el array completo
            List<RawCountry> raw = mapper.readValue(is, new TypeReference<List<RawCountry>>() {});
            // Mapeamos a tu DTO “falso”
            return raw.stream()
                    .map(r -> new CountryPhone(r.getName(), r.getFlag(), r.getCode(), r.getDialcode()))
                    .toList();
        } catch (Exception e) {
            throw new RuntimeException("No se pudo leer countries.json", e);
        }
	}


	static class RawCountry {
        private String name;
        private String flag;
        private String code;
        private String dialCode;
 
        

        public String getName() { return name; }
        public String getFlag() { return flag; }
        public String getCode() { return code; }
        public String getDialcode() { return dialCode; }

        public void setName(String name) { this.name = name; }
        public void setFlag(String flag) { this.flag = flag; }
        public void setCode(String code) { this.code = code; }
        public void setDial_code(String dial_code) { this.dialCode = dial_code; }
		public String getDialCode() {
			return dialCode;
		}
		public void setDialCode(String dialCode) {
			this.dialCode = dialCode;
		}
        
        
    }
}
