import React, {useEffect, useState} from "react";
import Navbar from "../../components/Navbar";
import {
    Wrapper,
    MainContainer,
    PageContainer,
    ImageHeader, 
    ClassroomContainer, 
    ClassroomSidePanel, 
    ClassContentSide,
    ClassStatus,
    ClassroomCenterPanel,
    SharedFilesContainer,
    SubjectsRow,
    InputPostContainer,
    BigBox,
    PostBox,
    ButtonHolder,
} from "./Classroom.styles";
import Banner from '../../assets/img/matematica_banner.png';
import Button from "../../components/Button";
import Textarea from "../../components/Textarea";
import {classroomService} from "../../services"
import {useParams} from "react-router-dom";

const Classroom = () => {
    const teach = Math.round(Math.random());
    const profile = Math.random();
    const files = 1;
    const finished = 0;
    const [classInfo, setClassInfo] = useState();
    const id = useParams();

    useEffect( () => {
        classroomService.fetchClassroomInfo(id.id)
            .then(data => {
                setClassInfo(data);
            })
            .catch(err => console.log(err))
    }, [])


    let currId = 1;
    return (
        <Wrapper>
            <Navbar/>
            <MainContainer>
                {classInfo &&
                <PageContainer>
                    <ImageHeader src={Banner} alt={'banner'}/>
                    <ClassroomContainer>
                        <ClassroomSidePanel>
                            <ClassContentSide>
                                <h2>Classroom</h2>
                                <p>Subject: {classInfo.subjectName}</p>
                                {(teach === 1) ?
                                    <p>Student: {classInfo.student.name}</p>
                                    :
                                    <p>Teacher: {classInfo.teacher.name}</p>
                                }
                                <p>Price: ${classInfo.price}/hour</p>
                            </ClassContentSide>
                            <ClassContentSide>
                                {(profile > 0.66) ?
                                    <ClassStatus style={{background: "darkorange"}}>
                                        <h6 style={{color: "black", margin: "0"}}>Pending</h6>
                                    </ClassStatus>
                                    : (profile > 0.33) ? <ClassStatus style={{background: "green"}}>
                                            <h6 style={{color: "black", margin: "0"}}>Active</h6>
                                        </ClassStatus>
                                        : <ClassStatus style={{background: "white"}}>
                                            <h6 style={{color: "black", margin: "0"}}>Finished</h6>
                                        </ClassStatus>
                                }
                                {classInfo.status === 0 ? (
                                    <>
                                        {classInfo.teacher.id === currId ? (
                                            <></>
                                        ) : (
                                            <Button text={"Accept"}/>
                                        )}
                                        <Button text={"Cancel"}/>
                                    </>
                                ) : (
                                    <Button text={"Finish"}/>
                                )}
                            </ClassContentSide>
                        </ClassroomSidePanel>
                        <ClassroomCenterPanel>
                            {(finished === 0) ?
                                <InputPostContainer>
                                    <Textarea type="text" style={{
                                        borderRadius: "10px",
                                        fontSize: "16px",
                                        fontFamily: "Roboto Light, sans-serif",
                                        height: "fit-content",
                                        width: "100%",
                                        margin: "8px 0 8px 0",
                                        resize: "none"
                                    }}>
                                    </Textarea>
                                    <div style={{
                                        marginTop: "10px",
                                        width: "100%",
                                        display: "flex",
                                        flexDirection: "row",
                                        justifyContent: "space-between"
                                    }}>
                                        <div style={{
                                            display: "flex",
                                            flexDirection: "row",
                                            justifyContent: "center",
                                            alignItems: "center"
                                        }}>
                                            <Button text="Upload file" fontSize="15px">
                                                <input type="file" accept="image/*,.pdf" name="file"
                                                       style={{display: "none"}}
                                                       path="file"
                                                       id="file"/>
                                            </Button>
                                            <p style={{margin: "0 5px 0"}} id="fileName"></p>
                                        </div>
                                        <Button fontSize="15px" text="Publish"/>
                                    </div>
                                </InputPostContainer>
                                :
                                <div></div>
                            }
                            <BigBox>
                                <PostBox>
                                    <ButtonHolder>
                                        <h3>Gaston De Schant</h3>
                                        <p style={{fontSize: "0.8em"}}>Hoy a las 16:32</p>
                                    </ButtonHolder>
                                    <p>Buenos dias profesor necesito ayuda el miercoles a las 5 de la tarde podria ser
                                        ?</p>
                                    <a target="_blank" href=""
                                       style={{
                                           color: "blue",
                                           textDecoration: "underline",
                                           marginTop: "5px"
                                       }}>tarea.pdf</a>
                                </PostBox>
                            </BigBox>
                        </ClassroomCenterPanel>
                        <ClassroomSidePanel>
                            <ClassContentSide>
                                <h2>My Files</h2>
                                {(files === 0) ?
                                    <span style={{alignSelf: "center", margin: "8px 0 4px 0", fontSize: "20px"}}>There are no files to share.</span>
                                    :
                                    <div>
                                        <h6 style={{margin: "2px 0 2px 0"}}>Choose the files you want to share in this
                                            class</h6>
                                        <SharedFilesContainer>
                                            <ul style={{padding: 0, margin: 0}}>
                                                <SubjectsRow>
                                                    <li>
                                                        <a style={{fontWeight: "bold"}}
                                                           href="${pageContext.request.contextPath}/classFile/${currentClass.classId}/${file.fileId}"
                                                           target="_blank">File name</a>
                                                        <input type="checkbox" name="sharedFiles"
                                                               class="form-check-input"
                                                               style={{
                                                                   width: "18px",
                                                                   height: "18px",
                                                                   marginRight: "4px"
                                                               }}
                                                               value="${file.fileId}"
                                                               onclick="showButton(this.name,'share-button')"/>
                                                    </li>
                                                </SubjectsRow>
                                            </ul>
                                            <Button text="Share Files" fontSize="8" type="submit" id="share-button"
                                                    style={{alignSelf: "center", marginTop: "10px", display: "none"}}>
                                            </Button>
                                        </SharedFilesContainer>
                                    </div>
                                }
                            </ClassContentSide>
                            <ClassContentSide>
                                <h2>Shared files</h2>
                                {(files === 0) ?
                                    <span style={{alignSelf: "center", margin: "8px 0 4px 0", fontSize: "20px"}}>You haven't shared any files yet</span>
                                    :
                                    <div>

                                        <SharedFilesContainer>
                                            <ul style={{padding: 0, margin: 0}}>
                                                <SubjectsRow>
                                                    <li>
                                                        <a style={{fontWeight: "bold"}}
                                                           href="${pageContext.request.contextPath}/classFile/${currentClass.classId}/${file.fileId}"
                                                           target="_blank">File name</a>
                                                        <input type="checkbox" name="sharedFiles"
                                                               class="form-check-input"
                                                               style={{
                                                                   width: "18px",
                                                                   height: "18px",
                                                                   marginRight: "4px"
                                                               }}
                                                               value="${file.fileId}"
                                                               onclick="showButton(this.name,'share-button')"/>
                                                    </li>
                                                </SubjectsRow>
                                            </ul>
                                            <Button text="Stop sharing" fontSize="8" type="submit" id="share-button"
                                                    style={{alignSelf: "center", marginTop: "10px", display: "none"}}>
                                            </Button>
                                        </SharedFilesContainer>
                                    </div>
                                }
                            </ClassContentSide>
                            <ClassContentSide>
                                <h2>Class files</h2>
                                {(files === 0) ?
                                    <span style={{alignSelf: "center", margin: "8px 0 4px 0", fontSize: "20px"}}>Teacher hasn't shared files yet</span>
                                    :
                                    <SharedFilesContainer>
                                        <ul style={{padding: 0, margin: 0}}>
                                            <SubjectsRow>
                                                <li>
                                                    <a style={{fontWeight: "bold"}}
                                                       href="${pageContext.request.contextPath}/classFile/${currentClass.classId}/${file.fileId}"
                                                       target="_blank">File name</a>
                                                </li>
                                            </SubjectsRow>
                                        </ul>
                                    </SharedFilesContainer>
                                }
                            </ClassContentSide>
                        </ClassroomSidePanel>
                    </ClassroomContainer>
                </PageContainer>
                }
            </MainContainer>
        </Wrapper>
    )
}


export default Classroom;