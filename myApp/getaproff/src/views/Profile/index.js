import React, {useState} from "react";
import PropTypes from 'prop-types';
import i18next from "i18next";
import { useNavigate, useParams } from 'react-router-dom';

import Navbar from "../../components/Navbar";
import {
  InfoContainer,
  ProfileContainer,
  ProfileDataContainer,
  ProfileInfo,
  ProfileInfoButtons,
  ProfileName,
  ProfileSubjects,
  ReviewTitle,
  SectionInfo,
  StarsReviews,
  TabContainer,
  TabInfoContainer,
} from './Profile.styles';
import RatingStar from "react-stars";
import Button from "../../components/Button";
import Tab from "../../components/Tab";
import TabItem from "../../components/TabItem";
import Rows from "../../components/Rows";
import {Headers, MainContainer, Request, Row, Table, Wrapper} from "../../GlobalStyle";
import { favouritesService, filesService } from "../../services/index";
import Dropdown from "../../components/DropDown";
import { useProfileFetch } from "../../hooks/useProfileFetch";
import {Toast} from "react-bootstrap";
import {PageItem} from "react-bootstrap";
import {StyledPagination} from "../Tutors/Tutors.styles";
import { handleService } from "../../handlers/serviceHandler";

const Profile = () => {
  const editOptions = [{name: 'profile.edit.profile', path: '/edit-profile'}, 
    {name: 'profile.edit.certifications', path: '/edit-certifications'},
    {name: 'profile.edit.subjects', path: '/edit-subjects'}];

  const navigate = useNavigate();
  const { id } = useParams();
  const tabs = ['profile.personal', 'profile.subjects', 'profile.reviews'];

  const {
    currentUser,
    isFaved,
    image,
    user,
    isTeacher,
    reviews,
    certifications,
    index,
    loading,
    subjects,
    pageQty,
    page, 
    setPage,
    setIndex,
    setIsFaved
  } = useProfileFetch(id);

  let items = [];
  for (let number = 1; number <= pageQty; number++) {
    items.push(
        <PageItem
            key={number}
            active={number === page}
            onClick={() => setPage(number)}
        >
          {number}
        </PageItem>
    );
  };

  const handleOpenFile = async (fileId) => {
    const res = await filesService.getCertificationById(fileId);
    const data = handleService(res, navigate);
    window.open(URL.createObjectURL(new Blob([filesService.base64ToArrayBuffer(data.file)], { type: "application/pdf" })))
  }

  const handleFavoriteState = async () => {
    let teacherId = id;
    let setFavoriteStatus = favouritesService.addTeacherToFavorites;
    if (isFaved) {
      setFavoriteStatus = favouritesService.removeTeacherFromFavorites;
    }
    const res = await setFavoriteStatus(teacherId);
    handleService(res, navigate);
    setIsFaved(!isFaved);
  }
  const [show, setShow] = useState(false);

  const shareProfile = () => {
    navigator.clipboard.writeText(window.location.href).then(r => {
      setShow(true);
    });
  }
  const requestClass = () => {
    navigate(`/users/${id}/class-request`)
  }

  return (
    <Wrapper>
      <Navbar/>
      <MainContainer>
        {(loading)?
          <></>
        :
        <ProfileContainer>
          <InfoContainer>
            <img
              src={image}
              alt="profileImg"
            />
            <ProfileInfo>
              <ProfileName>
                <h1 style={{ fontSize: '2rem' }}>{user.name}</h1>
                {
                  isTeacher && 
                  <StarsReviews>
                    <RatingStar count={5} value={user.rate} size={18} edit={false} />
                    <p>({user.reviewsQty} {i18next.t('profile.reviews')})</p>
                  </StarsReviews>
                }
              </ProfileName>
              <Toast position='top-end' onClose={() => setShow(false)} show={show} delay={3000} autohide>
                <Toast.Body>{i18next.t('profile.shareSuccess')}</Toast.Body>
              </Toast>
              <ProfileInfoButtons>
                {currentUser && currentUser.id === user.id ? (
                  isTeacher ? 
                  <>
                    <Dropdown brand={i18next.t('profile.edit.edit')} options={editOptions} size="1rem" color="white" background="var(--secondary)" radius="2rem" padding="0.45em 1.3em"/>
                    <Button text={i18next.t('profile.share')} fontSize="1rem"/>
                  </> : 
                  <Button text={i18next.t('profile.editProfile')} fontSize="1rem" callback={() => navigate('/edit-profile')}/>
                ) : (
                  <>
                    <Button text={i18next.t('profile.request')} fontSize="1rem" callback={() => requestClass()}/>
                    <Button text={!isFaved ? i18next.t('profile.addFavourites') : i18next.t('profile.removeFavourites')} callback={handleFavoriteState} fontSize="1rem"/>
                    <Button text={i18next.t('profile.share')} callback={shareProfile} fontSize="1rem"/>
                  </>
                )}
              </ProfileInfoButtons>
            </ProfileInfo>
          </InfoContainer>
          {isTeacher && <TabContainer>
            <Tab setIndex={setIndex} style={{ margin: "2rem" }}>
              {tabs.map((tab, index) => {
                  return index === 0 ? <TabItem fontSize='1rem' style={{ borderTopLeftRadius: '0.625rem'}}>{i18next.t(tab)}</TabItem> : 
                    index === tabs.length - 1 ? <TabItem fontSize='1rem' style={{ borderTopRightRadius: '0.625rem'}}>{i18next.t(tab)}</TabItem> :
                   <TabItem fontSize='1rem'>{i18next.t(tab)}</TabItem>
              })}
            </Tab>
            <TabInfoContainer>
              {index === 0 ? (
                <SectionInfo>
                  <ProfileDataContainer>
                    <h3>{i18next.t('profile.description')}</h3>
                    <p>{user.description}</p>
                  </ProfileDataContainer>
                  <ProfileDataContainer>
                    <h3>{i18next.t('profile.schedule')}</h3>
                    <p>{user.schedule}</p>
                  </ProfileDataContainer>
                  <ProfileDataContainer> 
                    <h3>{i18next.t('profile.certifications')}</h3>
                    <ul>
                      {certifications.map((certification) => {
                        return (
                          // <a href="#" onClick={() => handleOpenFile(certification.id)}>{certification.name}</a>
                        <li key={certification.id}>
                          <Request>
                            <button onClick={() => handleOpenFile(certification.id)}>{certification.name}</button>
                          </Request>
                        </li>
                      )})}
                    </ul>
                  </ProfileDataContainer>
                </SectionInfo>
              ) : index === 1 ? (
                <ProfileSubjects>
                  <Table>
                      <thead>
                        <Row>
                          <Headers style={{ width: "50%" }}>{i18next.t('subjects.subject')}</Headers>
                          <Headers style={{ width: "20%" }}>{i18next.t('subjects.price')}</Headers>
                          <Headers style={{ width: "30%" }}>{i18next.t('subjects.level')}</Headers>
                        </Row>
                      </thead>
                      <tbody>
                        {subjects.map((item, index) => {
                            return <Rows key={index} edit={false} data={item}/>
                        })}
                      </tbody>
                    </Table>
                </ProfileSubjects>
              ) : (
                <SectionInfo>
                  {
                    reviews.map((option) => {
                      return <ProfileDataContainer>
                        <ReviewTitle>
                          <h3>{option.student}</h3>
                          <RatingStar count={5} value={option.rate} size={18} edit={false} />
                        </ReviewTitle>
                        <p>{option.review}</p>
                      </ProfileDataContainer>
                    })
                  }
                  {pageQty > 1 && <StyledPagination>{items}</StyledPagination>}
                </SectionInfo>
              )}
            </TabInfoContainer>
          </TabContainer>}
        </ProfileContainer>
        }
      </MainContainer>
    </Wrapper>
  );
};

Profile.propTypes = {
  id: PropTypes.number
};

export default Profile;
