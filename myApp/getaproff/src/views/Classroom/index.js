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
    BigBox,
    PostBox,
    ButtonHolder, Ul, ButtonContainer, PostFormContainer,
} from "./Classroom.styles";
import Banner from '../../assets/img/matematica_banner.png';
import Button from "../../components/Button";
import Textarea from "../../components/Textarea";
import {classroomService, filesService} from "../../services"
import authService from "../../services/authService";
import {useNavigate, useParams} from "react-router-dom";
import {useForm} from "react-hook-form";
import {StyledPagination} from "../Tutors/Tutors.styles";
import {PageItem} from "react-bootstrap";
import i18next from "i18next";
import { handleService } from "../../handlers/serviceHandler";

const Classroom = () => {
    const files = 1;
    const ACCEPTED = 1;
    const FINISHED = 2;
    const RATED = 3;
    const [classInfo, setClassInfo] = useState();
    const [classStatus, setClassStatus] = useState();
    const [classPosts, setClassPosts] = useState();
    const [refreshPosts, setRefreshPosts] = useState(true);
    const [page, setPage] = useState(1);
    const [pageQty, setPageQty] = useState(1);
    const [notSharedClassFiles, setNonSharedClassFiles] = useState();
    const [sharedClassFiles, setSharedClassFiles] = useState();
    const [fileVisibilityChanged, setFileVisibilityChanged] = useState(false);
    const [isTeacherClassroom, setIsTeacherClassroom] = useState(false);

    const {register, handleSubmit, watch, reset} = useForm();
    const {register: shareFilesRegister, handleSubmit: shareFilesHandleSubmit} = useForm();
    const {register: stopSharingFilesRegister, handleSubmit: stopSharingFilesHandleSubmit} = useForm();

    const id = useParams();
    const watchFileName = watch("file");
    const user = authService.getCurrentUser();
    const navigate = useNavigate();

    let items = [];
    for (let number = 1; number <= pageQty; number++) {
        items.push(
            <PageItem
                key={number}
                active={number === page}
                onClick={() => {
                    setPage(number);
                    setRefreshPosts(true);
                }}
            >
                {number}
            </PageItem>
        );
    }

    const acceptClass = async () =>{
        const res = await classroomService.changeClassStatus(id.id, ACCEPTED, user.id);
        handleService(res, navigate);
        setClassStatus(1);
    }

    const cancelClass = async () => {
        const res = await classroomService.changeClassStatus(id.id, FINISHED, user.id);
        handleService(res, navigate);
        setClassStatus(3);
    }

    const rateTeacher = () => {
        navigate(`/users/${classInfo.teacher.id}/reviews`)
    }

    const publishPost = async (data) => {
        const res = await classroomService.createPost(id.id, data, user.id);
        handleService(res, navigate);
        setPage(1);
        setRefreshPosts(true);
        reset();
    }

    const stopSharingFiles = async (data) => {
        setFileVisibilityChanged(!fileVisibilityChanged);
        const res = classroomService.stopSharingFile(data.filesToChangeVisibility,id.id);
        handleService(res, navigate);
    }

    const shareFiles = async (data) => {
        setFileVisibilityChanged(!fileVisibilityChanged);
        const res = await classroomService.startSharingFile(data.filesToChangeVisibility,id.id);
        handleService(res, navigate);
    }

    const navigateToMyClasses = () => {
        navigate(`/users/${user.id}/classes`)
    }

    useEffect(async () => {
        const res = await classroomService.fetchClassroomInfo(id.id);
        setClassInfo(handleService(res, navigate));
    }, [classStatus])

    useEffect(() => {
        if (classInfo && classInfo.teacher)
            setIsTeacherClassroom(classInfo.teacher.id === user.id)
    }, [classInfo])

    useEffect(async () => {
        const res = await classroomService.getClassroomFiles(id.id);
        const data = await handleService(res, navigate);
        setSharedClassFiles(data.shared);
        if(isTeacherClassroom) setNonSharedClassFiles(data.notShared);
    }, [isTeacherClassroom, fileVisibilityChanged])

    useEffect(async () => {
        if (refreshPosts) {
            const res = await classroomService.fetchClassroomPosts(id.id, page);
            if (!res.failure) setPageQty(parseInt(res.headers['x-total-pages']));
            setClassPosts(handleService(res, navigate));
            setRefreshPosts(false);
        }
    }, [refreshPosts])


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
                                <h2>{i18next.t('classroom.title')}</h2>
                                <p>{i18next.t('classroom.subject')}: {classInfo.subjectName}</p>
                                <p>{i18next.t('classroom.student')}: {classInfo.student.name}</p>
                                <p>{i18next.t('classroom.teacher')}: {classInfo.teacher.name}</p>
                                <p>{i18next.t('classroom.price')}: ${classInfo.price}/{i18next.t('classroom.hour')}</p>
                            </ClassContentSide>
                            <ClassContentSide>
                                {(classInfo.status === 0) ?
                                    <ClassStatus style={{background: "darkorange"}}>
                                        <h6 style={{color: "black", margin: "0"}}>{i18next.t('classroom.status.pending')}</h6>
                                    </ClassStatus>
                                    : (classInfo.status === 1) ? <ClassStatus style={{background: "green"}}>
                                            <h6 style={{color: "black", margin: "0"}}>{i18next.t('classroom.status.active')}</h6>
                                        </ClassStatus>
                                        : <> {classInfo.status === FINISHED &&
                                                    <ClassStatus style={{background: "#d3d3d3"}}>
                                                        <h6 style={{color: "black", margin: "0"}}>{i18next.t('classroom.status.finished')}</h6>
                                                    </ClassStatus>}
                                             {classInfo.status === RATED &&
                                                 <ClassStatus style={{background: "#d3d3d3"}}>
                                                 <h6 style={{color: "black", margin: "0"}}>{i18next.t('classroom.status.rated')}</h6>
                                                 </ClassStatus>
                                             }
                                        </>
                                }
                                {classInfo.status === 0 ? (
                                    <ButtonContainer>
                                        {isTeacherClassroom &&
                                            <Button text={i18next.t('classroom.accept')} callback={acceptClass}/>
                                        }
                                        <Button text={i18next.t('classroom.cancel')} color={'#FFC300'} fontColor={'black'}/>
                                    </ButtonContainer>
                                ) :
                                    classInfo.status !== FINISHED &&  classInfo.status !== RATED  && (
                                    <Button text={i18next.t('classroom.finish')} color={'#ffc107'} callback={cancelClass} fontColor={'black'}/>
                                )}
                            </ClassContentSide>
                        </ClassroomSidePanel>
                        <ClassroomCenterPanel>
                            {(classInfo.status !== FINISHED && classInfo.status !== RATED) ?
                                <PostFormContainer onSubmit={handleSubmit(publishPost)}>
                                    <Textarea name="postTextInput" register={register} placeholder={i18next.t('classroom.post.placeholder')} style={{
                                        borderRadius: "10px",
                                        fontSize: "16px",
                                        fontFamily: "Roboto Light, sans-serif",
                                        width: "100%",
                                        height: '20vh',
                                        margin: "8px 0 8px 0",
                                        resize: "none"
                                    }}>
                                    </Textarea>
                                    <ButtonContainer>
                                        <div style={{display: 'flex', 'flexDirection': 'row', 'alignItems': 'center'}}>
                                            <Button type="button" text={i18next.t('classroom.post.uploadFile')}
                                                    fontSize="15px"
                                                    callback={() => document.getElementById("file").click()}/>
                                            <input id="file" {...register("file")} type="file" accept="image/*,.pdf"
                                                   style={{display: "none"}}
                                            />
                                            <p style={{margin: "0 9px"}}
                                               id="fileName">{watchFileName && watchFileName[0] && watchFileName[0].name}</p>
                                        </div>
                                        <Button fontSize="15px" text={i18next.t('classroom.post.publish')}/>
                                    </ButtonContainer>
                                </PostFormContainer>
                                :
                                <div style={{
                                    display: 'flex',
                                    backgroundColor: 'white',
                                    padding: '10px 0',
                                    borderRadius: '10px',
                                    'flexDirection': 'column',
                                    'textAlign': 'center',
                                    'width': '90%',
                                    'justifyContent': 'space-between',
                                    'gap': '10px'
                                }}>
                                    <h2>{i18next.t('classroom.classOver')}</h2>
                                    <Button text={i18next.t('classroom.back')} callback={navigateToMyClasses}/>
                                    {!isTeacherClassroom && classInfo.status !== RATED &&
                                    <Button text={i18next.t('classroom.rate')} callback={rateTeacher}/>}
                                </div>
                            }
                            <BigBox>
                                {classPosts && classPosts.map((post, index) => {
                                    return (
                                        <PostBox key={index}>
                                            <ButtonHolder>
                                                <h3>{post.uploader}</h3>
                                                <p style={{fontSize: "0.8em"}}>{post.time.split('T')[0]}</p>
                                            </ButtonHolder>
                                            <p>{post.message}</p>
                                            {post.file &&
                                            <a target="_blank" href={post.file.uri}
                                               style={{
                                                   color: "blue",
                                                   textDecoration: "underline",
                                                   marginTop: "5px"
                                               }}>
                                                {post.file.name}
                                            </a>}
                                        </PostBox>
                                    )
                                })}
                            </BigBox>
                            {pageQty !== 1 && <StyledPagination>{items}</StyledPagination>}
                        </ClassroomCenterPanel>
                        <ClassroomSidePanel>
                            {(isTeacherClassroom && classInfo.status !== FINISHED) ?
                                <>
                                    <ClassContentSide>
                                        <h2>{i18next.t('classroom.files.myFiles')}</h2>
                                        {(files === 0) ?
                                            <span style={{
                                                alignSelf: "center",
                                                margin: "8px 0 4px 0",
                                                fontSize: "20px"
                                            }}>{i18next.t('classroom.files.noFiles')}</span>
                                            :
                                                <SharedFilesContainer>
                                                    <h6 style={{margin: "2px 0 2px 0"}}>{i18next.t('classroom.files.choose')}</h6>
                                                    {notSharedClassFiles &&
                                                    <form onSubmit={shareFilesHandleSubmit(shareFiles)}>
                                                        <Ul>
                                                            {notSharedClassFiles.map((file, index) => {
                                                                return (
                                                                    <li key={index}>
                                                                        <SubjectsRow>
                                                                            <a style={{fontWeight: "bold"}}
                                                                               href="${pageContext.request.contextPath}/classFile/${currentClass.classId}/${file.fileId}"
                                                                               target="_blank">{file.title}</a>
                                                                            <input {...shareFilesRegister("filesToChangeVisibility")}
                                                                                   type="checkbox"
                                                                                   className="form-check-input"
                                                                                   style={{
                                                                                       width: "18px",
                                                                                       height: "18px",
                                                                                       marginRight: "4px"
                                                                                   }}
                                                                                   value={file.rel}
                                                                            />
                                                                        </SubjectsRow>
                                                                    </li>
                                                                );
                                                            })}
                                                        </Ul>
                                                        <Button text={i18next.t('classroom.files.share')} fontSize="8"
                                                                type="submit" id="share-button"
                                                                style={{
                                                                    alignSelf: "center",
                                                                    marginTop: "10px",
                                                                    display: "none"
                                                                }}>
                                                        </Button>
                                                    </form>
                                                    }
                                                </SharedFilesContainer>
                                        }
                                    </ClassContentSide>
                                    <ClassContentSide>
                                        <h2>{i18next.t('classroom.files.shared')}</h2>
                                        {(files === 0) ?
                                            <span style={{
                                                alignSelf: "center",
                                                margin: "8px 0 4px 0",
                                                fontSize: "20px"
                                            }}>{i18next.t('classroom.files.empty')}</span>
                                            :
                                            <SharedFilesContainer>
                                                {sharedClassFiles &&
                                                <form onSubmit={stopSharingFilesHandleSubmit(stopSharingFiles)}>
                                                    <Ul>
                                                        {sharedClassFiles.map((file, index) => {
                                                            return (
                                                                <li key={index}>
                                                                    <SubjectsRow>
                                                                        <a style={{fontWeight: "bold"}}
                                                                           href="${pageContext.request.contextPath}/classFile/${currentClass.classId}/${file.fileId}"
                                                                           target="_blank">{file.title}</a>
                                                                        <input
                                                                            {...stopSharingFilesRegister("filesToChangeVisibility")}
                                                                            type="checkbox"
                                                                            className="form-check-input"
                                                                            style={{
                                                                                width: "18px",
                                                                                height: "18px",
                                                                                marginRight: "4px"
                                                                            }}
                                                                            value={file.rel}
                                                                            onClick={() => console.log('click')}/>
                                                                    </SubjectsRow>
                                                                </li>
                                                            );
                                                        })}
                                                    </Ul>
                                                    <Button text={i18next.t('classroom.files.stop')} fontSize="8"
                                                            type="submit" id="share-button"
                                                            style={{
                                                                alignSelf: "center",
                                                                marginTop: "10px",
                                                                display: "none"
                                                            }}>
                                                    </Button>
                                                </form>
                                                }
                                            </SharedFilesContainer>
                                        }
                                    </ClassContentSide>
                                </>
                                :
                                <ClassContentSide>
                                    <h2>{i18next.t('classroom.files.classFiles')}</h2>
                                    {(files === 0) ?
                                        <span style={{
                                            alignSelf: "center",
                                            margin: "8px 0 4px 0",
                                            fontSize: "20px"
                                        }}>{i18next.t('classroom.files.noSharedFiles')}</span>
                                        :
                                        <SharedFilesContainer>
                                            <Ul>
                                                <li>
                                                    <SubjectsRow>
                                                        <a style={{fontWeight: "bold"}}
                                                           href="${pageContext.request.contextPath}/classFile/${currentClass.classId}/${file.fileId}"
                                                           target="_blank">{i18next.t('classroom.files.name')}</a>
                                                    </SubjectsRow>
                                                </li>
                                            </Ul>
                                        </SharedFilesContainer>
                                    }
                                </ClassContentSide>
                            }
                        </ClassroomSidePanel>

                    </ClassroomContainer>
                </PageContainer>
                }
            </MainContainer>
        </Wrapper>
    )
}


export default Classroom;