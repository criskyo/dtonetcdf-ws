package com.dtonetcdf.logica;

import com.dtonetcdf.persistencia.ArchivoDTO;
import com.dtonetcdf.persistencia.VariableDTO;
import com.dtonetcdf.persistencia.MyVariableDTO;
import ucar.nc2.NetcdfFile;
import ucar.nc2.Variable;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class Sistema {

    private ArchivoDTO ArchivoDTO;
    private VariableDTO variableDTO;




    public ArchivoDTO getArchivoDTO() {
        if (ArchivoDTO == null) {
            ArchivoDTO = new ArchivoDTO();
        }
        return ArchivoDTO;
    }

    public void setArchivoDTO(ArchivoDTO archivoDTO) {
        ArchivoDTO = archivoDTO;
    }

    public VariableDTO getVariableDTO() {
        if(variableDTO==null){
            variableDTO = new VariableDTO();
        }
        return variableDTO;
    }

    public void setVariableDTO(VariableDTO variableDTO) {
        this.variableDTO = variableDTO;
    }
    
    public List<MyVariableDTO> getMyVariable(){
    	List<MyVariableDTO> listMyVariable= new ArrayList<>();
    	
    	for (Variable variable : ArchivoDTO.getVariables()) {
			MyVariableDTO myVariableDTO = new MyVariableDTO();
			myVariableDTO.setName((variable.getName()==null?"":variable.getName()));
			myVariableDTO.setDescripcion((variable.getDescription()==null?"":variable.getDescription()));
			myVariableDTO.setDataType((variable.getDataType()==null?"":variable.getDataType().toString()));
			myVariableDTO.setDimensions((variable.getDimensionsString()==null?"":variable.getDimensionsString().toString()));
			int[] size = variable.getShape();
            String shape= "";
            for (int i = 0; i < size.length; ++i) {
                System.out.print(" " + size[i]);
                shape = shape + " "+size[i];

            }
            myVariableDTO.setShape(shape);
			
			
			
			listMyVariable.add(myVariableDTO);
		}
    	
    	
    	
    	return listMyVariable;
    }

    public NetcdfFile abrirarchivo() throws IOException {
        getArchivoDTO().setNetcdfFile(NetcdfFile.open(getArchivoDTO().getRuta()));
        getArchivoDTO().setVariables(getArchivoDTO().getNetcdfFile().getVariables());
        return getArchivoDTO().getNetcdfFile();
    }
    
    public NetcdfFile abrirarchivo(byte[] bytes,String name) throws IOException {
        getArchivoDTO().setNetcdfFile(NetcdfFile.openInMemory(name, bytes));
        getArchivoDTO().setVariables(getArchivoDTO().getNetcdfFile().getVariables());
        return getArchivoDTO().getNetcdfFile();
    }
    
    public String getInfoVariable(String name) {
    	for (Variable variable : ArchivoDTO.getVariables()) {
    		if(variable.getName().equalsIgnoreCase(name)) {
    			getVariableDTO().setVarSeleccionada(variable);
    			break;
    		}
    	}
    	return getVariableDTO().getImprimible();
    }


}
