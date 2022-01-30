import React, {useRef, useState} from 'react'

import Navbar from '../../components/Navbar';
import {MainContainer, Wrapper} from "../Home/Home.styles";
import {MainDiv, SelectContainer} from "./MyFiles.styles";
import Button from "../../components/Button";
import Modal from 'react-bootstrap/Modal'
import "bootstrap/dist/css/bootstrap.min.css";
import Form from 'react-bootstrap/Form'

const MyFiles = () => {

    const inputFile = useRef(null)
    const [show, setShow] = useState(false);

    const handleClose = () => setShow(false);
    const handleShow = () => setShow(true);

    const openFile = () => { inputFile.current.click();}

    return (
        <Wrapper>
            <Navbar/>
            <MainContainer>
                <MainDiv>
                    <h1>Mis archivos</h1>
                    <Button callback={handleShow} text={'Agregar Archivos'}/>

                    <Modal show={show} onHide={handleClose} size="xl">
                        <Modal.Header closeButton>
                            <Modal.Title>Agregar Archivo</Modal.Title>
                        </Modal.Header>
                        <Modal.Body style={{background: '#9fedd7',
                            padding: '10px 20px 5px 20px'}}>
                            <div>
                                <input type='file' id='file' ref={inputFile} style={{display: 'none'}}/>
                                Elija en que clases quiere disponiblizar el archivo
                                <SelectContainer>
                                    <Form.Select aria-label="Select Materia">
                                        <option>Open this select menu</option>
                                        <option value="1">Arte</option>
                                        <option value="2">Fisica</option>
                                        <option value="3">Periodismo</option>
                                    </Form.Select>
                                    <Form.Select aria-label="Select Nivel">
                                        <option>Open this select menu</option>
                                        <option value="1">Todos</option>
                                        <option value="2">Principiante</option>
                                        <option value="3">Avanzado</option>
                                    </Form.Select>
                                </SelectContainer>
                                <Button text={"Seleccionar archivo"} callback={openFile}/>
                            </div>
                        </Modal.Body>
                        <Modal.Footer>
                            <Button callback={handleClose} text={"Cancelar"}/>
                            <Button callback={handleClose} text={"Guardar Cambios"}/>
                        </Modal.Footer>
                    </Modal>
                </MainDiv>

            </MainContainer>
        </Wrapper>

    );
}
export default MyFiles;