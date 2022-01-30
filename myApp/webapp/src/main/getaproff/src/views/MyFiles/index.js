import React, {useRef, useState} from 'react'

import Navbar from '../../components/Navbar';
import {MainContainer, Wrapper} from "../Home/Home.styles";
import {MainDiv, SelectContainer} from "./MyFiles.styles";
import Button from "../../components/Button";
import Modal from 'react-bootstrap/Modal'
import "bootstrap/dist/css/bootstrap.min.css";
import Form from 'react-bootstrap/Form';
import FileItem from "../../components/FileItem";
import './style.css'

const MyFiles = () => {

    const inputFile = useRef(null)
    const [show, setShow] = useState(false);

    const handleClose = () => setShow(false);
    const handleShow = () => setShow(true);

    const openFile = () => {
        inputFile.current.click();
    }

    //Styles

    const rowTitle = {
        textAlign: 'start',
        fontSize: '20px',
        fontWeight: 'bold',
        fontFamily: 'Roboto Light, sans-serif'
    }

    const formTitle = {
        fontSize: '32px',
        fontWeight: 'bold',
        fontFamily: 'Roboto Light, sans-serif',
        color: '#026670'
    }

    const modalSelectLabel = {
        fontSize: '16px',
        formTitle: 'Roboto Light, sans-serif',
        color: '#026670'
    }

    return (
        <Wrapper>
            <Navbar/>
            <MainContainer>
                <MainDiv>
                    <h1 style={formTitle}>Mis archivos</h1>
                    <h5 style={{
                        margin: '10px 0 10px 0', color: '#026670'
                    }}>
                        Filtrar por:
                    </h5>
                    <div style={{width: '90%', margin: '15px 0 25px 0'}}>
                        <SelectContainer>
                            <Form.Group className="d-flex align-items-center w-100 mr-4">
                                <Form.Label className="px-2 mb-0" style={modalSelectLabel}>Materia: </Form.Label>
                                <Form.Select style={{borderRadius: '10px'}} aria-label="Select Materia">
                                    <option>Open this select menu</option>
                                    <option value="1">Arte</option>
                                    <option value="2">Fisica</option>
                                    <option value="3">Periodismo</option>
                                </Form.Select>
                            </Form.Group>
                            <Form.Group className="d-flex align-items-center w-100">
                                <Form.Label className="px-2 mb-0" style={modalSelectLabel}>Nivel: </Form.Label>
                                <Form.Select style={{borderRadius: '10px'}} aria-label="Select Nivel">
                                    <option>Open this select menu</option>
                                    <option value="1">Todos</option>
                                    <option value="2">Principiante</option>
                                    <option value="3">Avanzado</option>
                                </Form.Select>
                            </Form.Group>
                        </SelectContainer>
                    </div>
                    <table className="subjects-table">
                        <tbody>
                        <tr>
                            <tr className="subjects-row">
                                <td className="rowTitle" style={{width: '40%'}}>Archivo</td>
                                <td className="rowTitle" style={{width: '15%'}}>Materia</td>
                                <td className="rowTitle" style={{width: '26%'}}>Nivel</td>
                                <td className="rowTitle" style={{width: 'auto'}}>Selección</td>
                            </tr>
                        </tr>
                        </tbody>
                    </table>
                    <Button callback={handleShow} text={'Agregar Archivos'}/>

                    <Modal show={show} onHide={handleClose} size="xl">
                        <Modal.Header closeButton>
                            <Modal.Title style={formTitle}>Agregar Archivo</Modal.Title>
                        </Modal.Header>
                        <Modal.Body className="p-4" style={{
                            background: '#9fedd7',
                            display: 'flex',
                            flexDirection: 'column',
                            justifyContent: 'space-around'
                        }}>
                            <input type='file' id='file' ref={inputFile} style={{display: 'none'}}/>
                            <h3 style={{
                                alignSelf: 'center',
                                color: '#026670',
                                margin: '5px 0 15px 0',
                                fontWeight: '400',
                                fontFamily: 'Roboto Light, sans-serif',
                            }}>
                                Elija en que clases quiere disponiblizar el archivo
                            </h3>
                            <SelectContainer>
                                <Form.Group className="d-flex align-items-center w-100 mr-4">
                                    <Form.Label className="px-2 mb-0" style={modalSelectLabel}>Materia: </Form.Label>
                                    <Form.Select style={{borderRadius: '10px'}} aria-label="Select Materia">
                                        <option>Open this select menu</option>
                                        <option value="1">Arte</option>
                                        <option value="2">Fisica</option>
                                        <option value="3">Periodismo</option>
                                    </Form.Select>
                                </Form.Group>
                                <Form.Group className="d-flex align-items-center w-100">
                                    <Form.Label className="px-2 mb-0" style={modalSelectLabel}>Nivel: </Form.Label>
                                    <Form.Select style={{borderRadius: '10px'}} aria-label="Select Nivel">
                                        <option>Open this select menu</option>
                                        <option value="1">Todos</option>
                                        <option value="2">Principiante</option>
                                        <option value="3">Avanzado</option>
                                    </Form.Select>
                                </Form.Group>
                            </SelectContainer>
                            <ul style={{width: '100%', marginTop: '45px'}}>
                                <FileItem fileName={"Archivo1.pdf"}/>
                                <FileItem fileName={"Archivo2.pdf"}/>
                            </ul>
                            <div style={{marginTop: '25px', alignSelf: 'center'}}>
                                <Button text={"Elegir archivos"} fontSize={'16px'} callback={openFile}/>
                            </div>

                        </Modal.Body>
                        <Modal.Footer>
                            <Button color={'grey'} fontSize={'16px'} callback={handleClose} text={"Cancelar"}/>
                            <Button callback={handleClose} fontSize={'16px'} text={"Guardar Cambios"}/>
                        </Modal.Footer>
                    </Modal>
                </MainDiv>

            </MainContainer>
        </Wrapper>

    );
}
export default MyFiles;