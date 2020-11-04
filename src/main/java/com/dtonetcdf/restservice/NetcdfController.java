package com.dtonetcdf.restservice;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.dtonetcdf.logica.Sistema;
import com.dtonetcdf.persistencia.MyVariableDTO;

@RestController
public class NetcdfController {
	
	
	@Autowired
	private Sistema sistema;
	
	@RequestMapping(value = "/upload", method = RequestMethod.POST)
	public @ResponseBody String handleFileUpload(@RequestParam(value = "file") MultipartFile file) throws IOException {

		String filename = "outN.1";
		byte[] bytes = file.getBytes();
		sistema.abrirarchivo(bytes, filename);

		return "file uploaded";
}

	@GetMapping("/getvariables")
	public List<MyVariableDTO> getListVariables() {

			return sistema.getMyVariable();

	}
	
	@GetMapping("/getvariable")
	public  String getVariable(@RequestParam(value = "name")String name) {
		return sistema.getInfoVariable(name);
	}

}
